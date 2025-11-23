package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Storage-related infrastructure errors.
 * Context Code: 010
 * Error Code Range: 1-010-000 to 1-010-999
 *
 * These errors occur during local storage operations (database, file system, cache).
 */
sealed interface StorageErrorCatalog : ErrorCatalog {

    /**
     * Storage is completely unavailable.
     * Error Code: 1-010-000
     */
    @Serializable
    data object Unavailable : StorageErrorCatalog {
        override val errorCode: Int = 1010000
        override val message: String = "Storage is unavailable. Please try again later."
    }

    /**
     * Insufficient storage space.
     * Error Code: 1-010-001
     */
    @Serializable
    data object InsufficientSpace : StorageErrorCatalog {
        override val errorCode: Int = 1010001
        override val message: String = "Insufficient storage space. Please free up space and try again."
    }

    /**
     * Database corruption detected.
     * Error Code: 1-010-002
     */
    @Serializable
    data object DatabaseCorrupted : StorageErrorCatalog {
        override val errorCode: Int = 1010002
        override val message: String = "Database error detected. Please contact support."
    }

    /**
     * Read operation failed.
     * Error Code: 1-010-100
     */
    @Serializable
    data object ReadFailed : StorageErrorCatalog {
        override val errorCode: Int = 1010100
        override val message: String = "Failed to read data. Please try again."
    }

    /**
     * Write operation failed.
     * Error Code: 1-010-200
     */
    @Serializable
    data object WriteFailed : StorageErrorCatalog {
        override val errorCode: Int = 1010200
        override val message: String = "Failed to save data. Please try again."
    }

    /**
     * Delete operation failed.
     * Error Code: 1-010-300
     */
    @Serializable
    data object DeleteFailed : StorageErrorCatalog {
        override val errorCode: Int = 1010300
        override val message: String = "Failed to delete data. Please try again."
    }

    /**
     * Generic storage error (fallback).
     * Error Code: 1-010-999
     */
    @Serializable
    data object Unknown : StorageErrorCatalog {
        override val errorCode: Int = 1010999
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
    }
}
