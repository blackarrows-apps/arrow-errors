package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Storage-related errors.
 * Category Code: 11
 * Error Code Range: 11-000 to 11-999
 *
 * These errors occur during local storage operations (database, file system, cache).
 */
sealed interface StorageErrorCatalog : ErrorCatalog {

    /**
     * Storage is completely unavailable.
     * Error Code: 11-000
     */
    @Serializable
    data object Unavailable : StorageErrorCatalog {
        override val errorCode: Int = 11000
        override val message: String = "Storage is unavailable. Please try again later."
    }

    /**
     * Insufficient storage space.
     * Error Code: 11-001
     */
    @Serializable
    data object InsufficientSpace : StorageErrorCatalog {
        override val errorCode: Int = 11001
        override val message: String = "Insufficient storage space. Please free up space and try again."
    }

    /**
     * Database corruption detected.
     * Error Code: 11-002
     */
    @Serializable
    data object DatabaseCorrupted : StorageErrorCatalog {
        override val errorCode: Int = 11002
        override val message: String = "Database error detected. Please contact support."
    }

    /**
     * Read operation failed.
     * Error Code: 11-100
     */
    @Serializable
    data object ReadFailed : StorageErrorCatalog {
        override val errorCode: Int = 11100
        override val message: String = "Failed to read data. Please try again."
    }

    /**
     * Write operation failed.
     * Error Code: 11-200
     */
    @Serializable
    data object WriteFailed : StorageErrorCatalog {
        override val errorCode: Int = 11200
        override val message: String = "Failed to save data. Please try again."
    }

    /**
     * Delete operation failed.
     * Error Code: 11-300
     */
    @Serializable
    data object DeleteFailed : StorageErrorCatalog {
        override val errorCode: Int = 11300
        override val message: String = "Failed to delete data. Please try again."
    }

    /**
     * Generic storage error (fallback).
     * Error Code: 11-999
     */
    @Serializable
    data object Unknown : StorageErrorCatalog {
        override val errorCode: Int = 11999
        override val message: String = "Storage error occurred. Please try again."
    }

    companion object {
        /**
         * Returns a map of all storage error catalog entries indexed by error code.
         * Used by ErrorProvider for O(1) lookup performance.
         */
        fun registry(): Map<Int, ErrorCatalog> = mapOf(
            Unavailable.errorCode to Unavailable,
            InsufficientSpace.errorCode to InsufficientSpace,
            DatabaseCorrupted.errorCode to DatabaseCorrupted,
            ReadFailed.errorCode to ReadFailed,
            WriteFailed.errorCode to WriteFailed,
            DeleteFailed.errorCode to DeleteFailed,
            Unknown.errorCode to Unknown,
        )

        /**
         * Maps error codes to their corresponding message keys for i18n support.
         *
         * This map is used to convert error codes from the catalog into message keys
         * that can be resolved using [io.blackarrows.errors.catalog.i18n.MessageResolver].
         */
        fun messageKeyMapping(): Map<Int, String> = mapOf(
            Unavailable.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.STORAGE_UNAVAILABLE,
            InsufficientSpace.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.STORAGE_INSUFFICIENT_SPACE,
            DatabaseCorrupted.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.STORAGE_DATABASE_CORRUPTED,
            ReadFailed.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.STORAGE_READ_FAILED,
            WriteFailed.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.STORAGE_WRITE_FAILED,
            DeleteFailed.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.STORAGE_DELETE_FAILED,
            Unknown.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.STORAGE_UNKNOWN,
        )
    }
}
