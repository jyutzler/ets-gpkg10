package org.opengis.cite.gpkg10;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Luke Lambert
 */
public final class TableVerifier
{
    // Not meant to instantiate
    private TableVerifier()
    {

    }

    public static void verifyTable(final Connection connection, final TableDefinition table) throws SQLException
    {
        verifyTableDefinition(connection, table.getName());

        final Set<UniqueDefinition> uniques = getUniques(connection, table.getName());

        verifyColumns(connection,
                      table.getName(),
                      table.getColumns(),
                      uniques);

        verifyForeignKeys(connection,
                          table.getName(),
                          table.getForeignKeys());

        verifyGroupUniques(table.getName(),
                           table.getGroupUniques(),
                           uniques);
    }

    private static void verifyTableDefinition(final Connection connection, final String tableName) throws SQLException
    {
        try(final PreparedStatement statement = connection.prepareStatement("SELECT sql FROM sqlite_master WHERE (type = 'table' OR type = 'view') AND tbl_name = ?;"))
        {
            statement.setString(1, tableName);

            try(ResultSet gpkgContents = statement.executeQuery())
            {
                if(gpkgContents.getString("sql") == null)
                {
                    throw new RuntimeException(String.format("The `sql` field must include the %s table SQL Definition.", tableName));  // TODO this needs to be in the error string table
                }
            }
        }
    }

    private static Set<UniqueDefinition> getUniques(final Connection connection, final String tableName) throws SQLException
    {
        try(final Statement statement = connection.createStatement();
            final ResultSet indices   = statement.executeQuery(String.format("PRAGMA index_list(%s);", tableName)))
        {
            final Set<UniqueDefinition> uniqueDefinitions = new HashSet<>();

            while(indices.next())
            {
                final String indexName = indices.getString("name");
                try(Statement nameStatement = connection.createStatement();
                    ResultSet namesSet      = nameStatement.executeQuery(String.format("PRAGMA index_info(%s);", indexName)))
                {
                    final List<String> names = new ArrayList<>();

                    while(namesSet.next())
                    {
                        names.add(namesSet.getString("name"));
                    }

                    uniqueDefinitions.add(new UniqueDefinition(names));
                }
            }

            return uniqueDefinitions;
        }
    }

    private static void verifyColumns(final Connection                    connection,
                                      final String                        tableName,
                                      final Map<String, ColumnDefinition> requiredColumns,
                                      final Collection<UniqueDefinition>  uniques) throws SQLException, AssertionError
    {
        try(final Statement statement = connection.createStatement();
            final ResultSet tableInfo = statement.executeQuery(String.format("PRAGMA table_info(%s);", tableName)))
        {
            final Map<String, ColumnDefinition> columns = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

            while(tableInfo.next())
            {
                final String columnName = tableInfo.getString("name");
                columns.put(columnName,
                            new ColumnDefinition(tableInfo.getString ("type"),
                                                 tableInfo.getBoolean("notnull"),
                                                 tableInfo.getBoolean("pk"),
                                                 uniques.stream().anyMatch(unique -> unique.equals(columnName)),
                                                 tableInfo.getString ("dflt_value")));   // TODO manipulate values so that they're "normalized" sql expressions, e.g. "" -> '', strftime ( '%Y-%m-%dT%H:%M:%fZ' , 'now' ) -> strftime('%Y-%m-%dT%H:%M:%fZ','now')
            }

            // Make sure the required fields exist in the table
            for(final Map.Entry<String, ColumnDefinition> column : requiredColumns.entrySet())
            {
                if(!columns.containsKey(column.getKey()))
                {
                    throw new RuntimeException(String.format("Required column: %s.%s is missing", tableName, column.getKey()));  // TODO this needs to be in the error string table
                }

                final ColumnDefinition columnDefinition = columns.get(column.getKey());

                if(columnDefinition != null)
                {
                    if(columnDefinition.equals(column.getValue()))
                    {
                        throw new RuntimeException(String.format("Required column %s is defined as:\n%s\nbut should be:\n%s",
                                                   column.getKey(),
                                                   columnDefinition.toString(),
                                                   column.getValue().toString()));
                    }
                }
            }
        }
    }

    private static void verifyForeignKeys(final Connection                connection,
                                          final String                    tableName,
                                          final Set<ForeignKeyDefinition> requiredForeignKeys) throws AssertionError, SQLException
    {
        try(final Statement statement = connection.createStatement())
        {
            try(final ResultSet fkInfo = statement.executeQuery(String.format("PRAGMA foreign_key_list(%s);", tableName)))
            {
                final List<ForeignKeyDefinition> foundForeignKeys = new LinkedList<>();

                while(fkInfo.next())
                {
                    foundForeignKeys.add(new ForeignKeyDefinition(fkInfo.getString("table"),
                                                                  fkInfo.getString("from"),
                                                                  fkInfo.getString("to")));
                }

                final Collection<ForeignKeyDefinition> missingKeys = new HashSet<>(requiredForeignKeys);
                missingKeys.removeAll(foundForeignKeys);

                final Collection<ForeignKeyDefinition> extraneousKeys = new HashSet<>(foundForeignKeys);
                extraneousKeys.removeAll(requiredForeignKeys);

                final StringBuilder error = new StringBuilder();

                if(!missingKeys.isEmpty())
                {
                    error.append(String.format("The table %s is missing the foreign key constraint(s): \n", tableName));
                    for(final ForeignKeyDefinition key : missingKeys)
                    {
                        error.append(String.format("%s.%s -> %s.%s\n",
                                                   tableName,
                                                   key.getFromColumnName(),
                                                   key.getReferenceTableName(),
                                                   key.getToColumnName()));
                    }
                }

                if(!extraneousKeys.isEmpty())
                {
                    error.append(String.format("The table %s has extraneous foreign key constraint(s): \n", tableName));
                    for(final ForeignKeyDefinition key : extraneousKeys)
                    {
                        error.append(String.format("%s.%s -> %s.%s\n",
                                                   tableName,
                                                   key.getFromColumnName(),
                                                   key.getReferenceTableName(),
                                                   key.getToColumnName()));
                    }
                }

                if(error.length() != 0)
                {
                    throw new RuntimeException(error.toString());     // TODO this needs to be in the error string table
                }
            }
            catch(final SQLException ignored)
            {
                // If a table has no foreign keys, executing the query
                // PRAGMA foreign_key_list(<table_name>) will throw an
                // exception complaining that result set is empty.
                // The issue has been posted about it here:
                // https://bitbucket.org/xerial/sqlite-jdbc/issue/162/
                // If the result set is empty (no foreign keys), there's no
                // work to be done.  Unfortunately .executeQuery() may throw an
                // SQLException for other reasons that may require some
                // attention.
            }
        }
    }

    private static void verifyGroupUniques(final String                       tableName,
                                           final Iterable<UniqueDefinition>   requiredGroupUniques,
                                           final Collection<UniqueDefinition> uniques) throws AssertionError
    {
        for(final UniqueDefinition groupUnique : requiredGroupUniques)
        {
            if(!uniques.contains(groupUnique))
            {
                throw new RuntimeException(String.format("The table %s is missing the column group unique constraint: (%s)",
                                           tableName,
                                           String.join(", ", groupUnique.getColumnNames())));
            }
        }
    }
}
