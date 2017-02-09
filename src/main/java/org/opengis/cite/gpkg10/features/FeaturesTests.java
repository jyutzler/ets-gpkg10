package org.opengis.cite.gpkg10.features;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.opengis.cite.gpkg10.CommonFixture;
import org.opengis.cite.gpkg10.ErrorMessage;
import org.opengis.cite.gpkg10.ErrorMessageKeys;
import org.opengis.cite.gpkg10.GPKG10;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Defines test methods that apply to descriptive information about a
 * GeoPackage's content as it pertains to features.
 *
 * <p style="margin-bottom: 0.5em">
 * <strong>Sources</strong>
 * </p>
 * <ul>
 * <li><a href="http://www.geopackage.org/spec/#features" target= "_blank">
 * GeoPackage Encoding Standard - 2.1. Tiles</a> (OGC 12-128r13)</li>
 * </ul>
 *
 * @author Jeff Yutzler
 */
public class FeaturesTests extends CommonFixture {
	/**
	 * Sets up variables used across methods
	 *
	 * @throws SQLException
	 *             if there is a database error
	 */
	@BeforeClass
	public void setUp() throws SQLException {
		try (Statement statement = this.databaseConnection.createStatement();
				ResultSet resultSet = statement.executeQuery(
						"SELECT table_name FROM gpkg_contents WHERE data_type = 'features';")) {
			while (resultSet.next()) {
				this.featureTableNames.add(resultSet.getString("table_name"));
			}
		}

		try (final Statement statement = this.databaseConnection.createStatement();
				final ResultSet resultSet = statement
						.executeQuery("SELECT table_name FROM gpkg_contents WHERE data_type = 'features';")) {
			while (resultSet.next()) {
				this.contentsFeatureTableNames.add(resultSet.getString(1));
			}
		}
	}

	/**
	 * Test case
	 * {@code /opt/features/vector_features/data/feature_table_integer_primary_key}
	 *
	 * @see <a href="requirement_feature_integer_pk" target= "_blank">Vector
	 *      Features User Data Tables - Requirement 29</a>
	 *
	 * @throws SQLException
	 *             If an SQL query causes an error
	 */
	@Test(description = "See OGC 12-128r12: Requirement 29")
	public void featureTableIntegerPrimaryKey() throws SQLException {
		for (final String tableName : this.featureTableNames) {
			final Statement statement = this.databaseConnection.createStatement();
			// 1
			final ResultSet resultSet = statement.executeQuery(String.format("PRAGMA table_info(%s);", tableName));

			// 2
			assertTrue(resultSet.next(),
					ErrorMessage.format(ErrorMessageKeys.FEATURES_TABLE_DOES_NOT_EXIST, tableName));

			boolean pass = false;
			// 3
			do {
				// While the spec requires "notnull", the DDL before V1.2 doesn't set it
				if (getGeopackageVersion().equals(GeoPackageVersion.V120)){
					if ((resultSet.getInt("pk") == 1) && (resultSet.getInt("notnull") == 1)
							&& (resultSet.getString("type").equals("INTEGER"))) {
						pass = true;
						break;
					}
				} else {
					if ((resultSet.getInt("pk") == 1) && (resultSet.getString("type").equals("INTEGER"))) {
						pass = true;
						break;
					}
				}
			} while (resultSet.next());

			assertTrue(pass, ErrorMessage.format(ErrorMessageKeys.FEATURE_TABLE_NO_PK, tableName));
		}
	}


	/**
	 * Test case
	 * {@code /opt/features/geometry_encoding/data/blob} and 
	 * {@code /opt/features/geometry_encoding/data/core_types_existing_sparse_data}
	 *
	 * @see <a href="_requirement-19" target= "_blank">Vector
	 *      Features BLOB Format - Requirement 19</a>
	 *
	 * @throws SQLException
	 *             If an SQL query causes an error
	 */
	@Test(description = "See OGC 12-128r12: Requirements 19, 20")
	public void featureGeometryEncodingTableBlob() throws SQLException {
		// 1
		final Statement statement1 = this.databaseConnection.createStatement();

		final ResultSet resultSet1 = statement1.executeQuery("SELECT table_name AS tn, column_name AS cn FROM gpkg_geometry_columns WHERE table_name IN (SELECT table_name FROM gpkg_contents WHERE data_type = 'features');");

		// 2
		while (resultSet1.next()){
			final Statement statement3 = this.databaseConnection.createStatement();
			final String cn = resultSet1.getString("cn");
			final String tn = resultSet1.getString("tn");
			// 3a
			final ResultSet resultSet3 = statement3.executeQuery(String.format("SELECT %s FROM %s;", cn, tn));
			
			// 3b
			while (resultSet3.next()){
		        // 3c
				final InputStream sgpb = resultSet3.getBinaryStream(cn);

				// 3ci
				final byte[] sgbpb = new byte[4];
				try {
					sgpb.read(sgbpb);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					assertTrue(false, ErrorMessage.format(ErrorMessageKeys.FEATURES_BINARY_INVALID, tn));;
				}
		        final byte[] gp = Arrays.copyOfRange(sgbpb, 0, 2);
		        assertTrue(Arrays.equals(gp, GPKG10.BINARY_GP), ErrorMessage.format(ErrorMessageKeys.FEATURES_BINARY_INVALID, tn));

		        // 3cii
		        assertTrue(sgbpb[2] == 0, ErrorMessage.format(ErrorMessageKeys.FEATURES_BINARY_INVALID, tn));
		        
		        // 3ciii
		        assertTrue((sgbpb[3] & 0b00100000) == 0, ErrorMessage.format(ErrorMessageKeys.FEATURES_BINARY_INVALID, tn));
		        
		        // 5bvii
		        final int envelope = (sgbpb[3] & 0b00001110) >> 1;
		        assertTrue(envelope <= 4, ErrorMessage.format(ErrorMessageKeys.FEATURES_BINARY_INVALID, tn));
			}
		}
	}

	/**
	 * Test case
	 * {@code /opt/features/geometry_columns/data/table_def}
	 *
	 * @see <a href="_requirement-21" target= "_blank">Vector
	 *      Features Geometry Columns Table - Requirement 21</a>
	 *
	 * @throws SQLException
	 *             If an SQL query causes an error
	 */
	@Test(description = "See OGC 12-128r12: Requirement 21")
	public void featureGeometryColumnsTableDef() throws SQLException {
		// 1
		final Statement statement = this.databaseConnection.createStatement();

		final ResultSet resultSet = statement.executeQuery("PRAGMA table_info('gpkg_geometry_columns');");

		// 2
		while (resultSet.next()){
			// 3
			final String name = resultSet.getString("name");
			if ("geometry_type_name".equals(name)){
				assertTrue("TEXT".equals(resultSet.getString("type")), ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("notnull") == 1, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("pk") == 0, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
			} else if ("table_name".equals(name)){
				assertTrue("TEXT".equals(resultSet.getString("type")), ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("notnull") == 1, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("pk") == 1, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
			} else if ("m".equals(name)){
				assertTrue("TINYINT".equals(resultSet.getString("type")), ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("notnull") == 1, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("pk") == 0, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
			} else if ("z".equals(name)){
				assertTrue("TINYINT".equals(resultSet.getString("type")), ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("notnull") == 1, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("pk") == 0, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
			} else if ("srs_id".equals(name)){
				assertTrue("INTEGER".equals(resultSet.getString("type")), ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("notnull") == 1, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("pk") == 0, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
			} else if ("column_name".equals(name)){
				assertTrue("TEXT".equals(resultSet.getString("type")), ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("notnull") == 1, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
				assertTrue(resultSet.getInt("pk") == 2, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_INVALID);
			}
		}
		
		// 4
		final Statement statement2 = this.databaseConnection.createStatement();

		final ResultSet resultSet2 = statement2.executeQuery("PRAGMA foreign_key_list('gpkg_geometry_columns');");
		
		boolean foundContents = false;
		boolean foundSpatialRefSys = false;

		while (resultSet2.next()){
			final String table = resultSet2.getString("table");
			if ("gpkg_spatial_ref_sys".equals(table)){
				if ("srs_id".equals(resultSet2.getString("from")) && "srs_id".equals(resultSet2.getString("to"))){
					foundSpatialRefSys = true;
				}
			} else if ("gpkg_contents".equals(table)){
				if ("table_name".equals(resultSet2.getString("from")) && "table_name".equals(resultSet2.getString("to"))){
					foundContents = true;
				}
			}
		}
		assertTrue(foundContents && foundSpatialRefSys, ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_NO_FK);
	}

	/**
	 * Test case
	 * {@code /opt/features/geometry_columns/data/table_def}
	 *
	 * @see <a href="_requirement-21" target= "_blank">Vector
	 *      Features Geometry Columns Table - Requirement 22</a>
	 *
	 * @throws SQLException
	 *             If an SQL query causes an error
	 */
	@Test(description = "See OGC 12-128r12: Requirement 22")
	public void featureGeometryColumnsDataValues() throws SQLException {
		// 1
		final Statement statement = this.databaseConnection.createStatement();

		final ResultSet resultSet = statement.executeQuery("SELECT table_name FROM gpkg_contents WHERE data_type = 'features';");

		// 2
		if (resultSet.next()){
			// 3
			final Statement statement2 = this.databaseConnection.createStatement();
			
			final ResultSet resultSet2 = statement2.executeQuery("SELECT table_name FROM gpkg_contents WHERE data_type = 'features' AND table_name NOT IN (SELECT table_name FROM gpkg_geometry_columns);");
			
			assertTrue(!resultSet2.next(), ErrorMessageKeys.FEATURES_GEOMETRY_COLUMNS_MISMATCH);
		}
	}
	
	private final Collection<String> featureTableNames = new ArrayList<>();
	private final Collection<String> contentsFeatureTableNames = new ArrayList<>();
}
