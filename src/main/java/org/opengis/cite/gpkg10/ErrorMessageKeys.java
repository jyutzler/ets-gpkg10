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
    public static final String FEATURES_TABLE_DOES_NOT_EXIST = "FeaturesTableDoesNotExist";
    public static final String FEATURE_TABLE_NO_PK = "FeatureTableNoPK";
    public static final String FEATURES_BINARY_INVALID = "FeaturesBinaryInvalid";
    public static final String FEATURES_GEOMETRY_COLUMNS_INVALID = "FeaturesGeometryColumnsInvalid";
    public static final String FEATURES_GEOMETRY_COLUMNS_NO_FK = "FeaturesGeometryColumnsNoFK";
    public static final String FEATURES_GEOMETRY_COLUMNS_MISMATCH = "FeaturesGeometryColumnsMismatch";
    public static final String FEATURES_GEOMETRY_COLUMNS_INVALID_COL = "FeaturesGeometryColumnsInvalidCol";
    public static final String FEATURES_GEOMETRY_COLUMNS_INVALID_GEOM = "FeaturesGeometryColumnsInvalidGeom";
    public static final String FEATURES_GEOMETRY_COLUMNS_INVALID_Z = "FeaturesGeometryColumnsInvalidZ";
    public static final String FEATURES_GEOMETRY_COLUMNS_INVALID_M = "FeaturesGeometryColumnsInvalidM";
    public static final String FEATURES_ONE_GEOMETRY_COLUMN = "FeaturesOneGeometryColumn";
    public static final String TILES_TABLES_NOT_REFERENCED_IN_CONTENTS = "TilesTablesNotReferencedInContents";
    public static final String VALUES_DO_NOT_VARY_BY_FACTOR_OF_TWO = "ValuesDoNotVaryByFactorOfTwo";
    public static final String INVALID_IMAGE_FORMAT = "InvalidImageFormat";
    public static final String TILE_MATRIX_SET_TABLE_DOES_NOT_EXIST = "TileMatrixSetTableDoesNotExist";
    public static final String BAD_TILE_MATRIX_SET_TABLE_DEFINITION = "BadTileMatrixSetTableDefinition";
    public static final String UNREFERENCED_TILE_MATRIX_SET_TABLE = "UnreferencedTileMatrixSetTable";
    public static final String UNREFERENCED_TILES_CONTENT_TABLE_NAME = "UnreferencedTilesContentTableName";
    public static final String BAD_MATRIX_SET_SRS_REFERENCE = "BadMatrixSetSrsReference";
    public static final String TILE_MATRIX_TABLE_DOES_NOT_EXIST = "TileMatrixTableDoesNotExist";
    public static final String BAD_TILE_MATRIX_TABLE_DEFINITION = "BadTileMatrixTableDefinition";
    public static final String BAD_MATRIX_CONTENTS_REFERENCES = "BadMatrixContentsReferences";
    public static final String MISSING_TILE_MATRIX_ENTRY = "MissingTileMatrixEntry";
    public static final String BAD_PIXEL_DIMENSIONS = "BadPixelDimensions";
    public static final String NEGATIVE_ZOOM_LEVEL = "NegativeZoomLevel";
    public static final String NON_POSITIVE_MATRIX_WIDTH = "NonPositiveMatrixWidth";
    public static final String NON_POSITIVE_MATRIX_HEIGHT = "NonPositiveMatrixHeight";
    public static final String NON_POSITIVE_TILE_WIDTH = "NonPositiveTileWidth";
    public static final String NON_POSITIVE_TILE_HEIGHT = "NonPositiveTileHeight";
    public static final String NON_POSITIVE_PIXEL_X_SIZE = "NonPositivePixelXSize";
    public static final String NON_POSITIVE_PIXEL_Y_SIZE = "NonPositivePixelYSize";
    public static final String PIXEL_SIZE_NOT_DECREASING = "PixelSizeNotDecreasing";
    public static final String BAD_TILE_PYRAMID_USER_DATA_TABLE_DEFINITION = "BadTilePyramidUserDataTableDefinition";
    public static final String UNDEFINED_ZOOM_LEVEL = "UndefinedZoomLevel";
    public static final String TILE_COLUMN_OUT_OF_RANGE = "TileColumnOutOfRange";
    public static final String TILE_ROW_OUT_OF_RANGE = "TileRowOutOfRange";
    public static final String BAD_METADATA_TABLE_DEFINITION = "BadMetadataTableDefinition";
    public static final String INVALID_METADATA_SCOPE = "InvalidMetadataScope";
    public static final String MISSING_METADATA_REFERENCE_TABLE = "MissingMetadataReferenceTable";
    public static final String BAD_METADATA_REFERENCE_TABLE_DEFINITION = "BadMetadataReferenceTableDefinition";
    public static final String INVALID_METADATA_REFERENCE_SCOPE = "InvalidMetadataReferenceScope";
    public static final String BAD_METADATA_REFERENCE_SCOPE_COLUMN_NAME_AGREEMENT = "BadMetadataReferenceScopeColumnNameAgreement";
    public static final String INVALID_METADATA_REFERENCE_TABLE = "InvalidMetadataReferenceTable";
}
