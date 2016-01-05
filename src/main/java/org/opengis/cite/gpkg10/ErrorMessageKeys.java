package org.opengis.cite.gpkg10;

/**
 * Defines keys used to access localized messages for assertion errors. The
 * messages are stored in Properties files that are encoded in ISO-8859-1
 * (Latin-1). For some languages the {@code native2ascii} tool must be used to
 * process the files and produce escaped Unicode characters.
 */
public class ErrorMessageKeys {

    public static final String NOT_SCHEMA_VALID = "NotSchemaValid";
    public static final String EMPTY_STRING = "EmptyString";
    public static final String XPATH_RESULT = "XPathResult";
    public static final String NAMESPACE_NAME = "NamespaceName";
    public static final String LOCAL_NAME = "LocalName";
    public static final String XML_ERROR = "XMLError";
    public static final String XPATH_ERROR = "XPathError";
    public static final String MISSING_INFOSET_ITEM = "MissingInfosetItem";
    public static final String UNEXPECTED_STATUS = "UnexpectedStatus";
    public static final String UNEXPECTED_MEDIA_TYPE = "UnexpectedMediaType";
    public static final String MISSING_ENTITY = "MissingEntity";
    public static final String INVALID_HEADER_STR = "InvalidHeaderString";
    public static final String UNKNOWN_APP_ID = "UnknownApplicationId";
    public static final String INVALID_SUFFIX = "InvalidSuffix";
    public static final String INVALID_DATA_TYPE = "InvalidDataType";
    public static final String PRAGMA_INTEGRITY_CHECK_NOT_OK = "PragmaIntegrityCheckNotOk";
    public static final String INVALID_FOREIGN_KEY = "InvalidForeignKey";
    public static final String NO_SQL_ACCESS = "NoSqlAccess";
    public static final String SQLITE_OMIT_OPTIONS = "SqliteOmitOptions";
    public static final String BAD_SRS_TABLE_DEFINITION = "BadSrsTableDefinition";
    public static final String NO_GEOGRAPHIC_SRS = "NoGeographicSrs";
    public static final String NO_UNDEFINED_CARTESIAN_SRS = "NoUndefinedCartesianSrs";
    public static final String NO_UNDEFINED_GEOGRAPHIC_SRS = "NoUndefinedGeographicSrs";
    public static final String UNDEFINED_SRS = "UndefinedSrs";
    public static final String CONTENT_TABLE_DOES_NOT_EXIST = "ContentTableDoesNotExist";
    public static final String BAD_CONTENTS_ENTRY_LAST_CHANGE_FORMAT = "BadContentsEntryLastChangeFormat";
    public static final String BAD_CONTENTS_TABLE_SRS_FOREIGN_KEY = "BadContentsTableSrsForeignKey";
    public static final String BAD_CONTENTS_TABLE_DEFINITION = "BadContentsTableDefinition";
    public static final String TILES_TABLE_NOT_REFERENCED_IN_CONTENTS = "TilesTableNotReferencedInContents";
    public static final String VALUES_DO_NOT_VARY_BY_FACTOR_OF_TWO = "ValuesDoNotVaryByFactorOfTwo";
    public static final String INVALID_IMAGE_FORMAT = "InvalidImageFormat";
    public static final String TILE_MATRIX_SET_TABLE_DOES_NOT_EXIST = "TileMatrixSetTableDoesNotExist";
    public static final String BAD_TILE_MATRIX_SET_TABLE_DEFINITION = "BadTileMatrixSetTableDefinition";
    public static final String UNREFERENCED_TILE_MATRIX_SET_TABLE = "UnreferencedTileMatrixSetTable";
    public static final String UNREFERENCED_TILES_CONTENT_TABLE_NAME = "UnreferencedTilesContentTableName";
    public static final String BAD_MATRIX_SET_SRS_REFERENCE = "BadMatrixSetSrsReference";
    public static final String TILE_MATRIX_TABLE_DOES_NOT_EXIST = "TileMatrixTableDoesNotExist";
    public static final String BAD_TILE_MATRIX_TABLE_DEFINITION = "BadTileMatrixTableDefinition";
    public static final String BAD_MATRIX_CONTENTS_REFERENCE = "BadMatrixContentsReference";
    public static final String MISSING_TILE_MATRIX_ENTRY = "MissingTileMatrixEntry";
}
