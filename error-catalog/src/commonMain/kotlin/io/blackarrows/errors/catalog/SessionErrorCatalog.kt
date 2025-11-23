package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Session domain errors.
 * Context Code: 200
 * Error Code Range: 2-200-000 to 2-200-999
 *
 * Session-related business logic errors.
 */
sealed interface SessionErrorCatalog : ErrorCatalog {

    // ============================================================================
    // Fetch Operations (2-200-000 to 2-200-099)
    // ============================================================================

    /**
     * Failed to fetch session due to network unavailability.
     * Error Code: 2-200-000
     */
    @Serializable
    data object FetchNetworkUnavailable : SessionErrorCatalog {
        override val errorCode: Int = 2200000
        override val message: String = "Failed to fetch session. Please check your connection and try again."
    }

    /**
     * Failed to fetch session due to storage error.
     * Error Code: 2-200-001
     */
    @Serializable
    data object FetchStorageError : SessionErrorCatalog {
        override val errorCode: Int = 2200001
        override val message: String = "Failed to fetch session due to storage error. Please try again."
    }

    /**
     * Session not found.
     * Error Code: 2-200-002
     */
    @Serializable
    data object FetchNotFound : SessionErrorCatalog {
        override val errorCode: Int = 2200002
        override val message: String = "Session not found. Please log in again."
    }

    /**
     * Session data is invalid or corrupted.
     * Error Code: 2-200-003
     */
    @Serializable
    data object FetchInvalidData : SessionErrorCatalog {
        override val errorCode: Int = 2200003
        override val message: String = "Session data is invalid. Please log in again."
    }

    /**
     * Session fetch timed out.
     * Error Code: 2-200-500
     */
    @Serializable
    data object FetchTimeout : SessionErrorCatalog {
        override val errorCode: Int = 2200500
        override val message: String = "Session fetch timed out. Please try again."
    }

    /**
     * Unknown fetch error.
     * Error Code: 2-200-999
     */
    @Serializable
    data object FetchUnknown : SessionErrorCatalog {
        override val errorCode: Int = 2200999
        override val message: String = "Failed to fetch session. Please try again."
    }

    // ============================================================================
    // Save Operations (2-200-100 to 2-200-199)
    // ============================================================================

    /**
     * Failed to save session due to storage error.
     * Error Code: 2-200-100
     */
    @Serializable
    data object SaveStorageError : SessionErrorCatalog {
        override val errorCode: Int = 2200100
        override val message: String = "Failed to save session. Please try again."
    }

    /**
     * Session validation failed during save.
     * Error Code: 2-200-400
     */
    @Serializable
    data object SaveValidationFailed : SessionErrorCatalog {
        override val errorCode: Int = 2200400
        override val message: String = "Session data is invalid. Please check your input."
    }

    /**
     * Unknown save error.
     * Error Code: 2-200-199
     */
    @Serializable
    data object SaveUnknown : SessionErrorCatalog {
        override val errorCode: Int = 2200199
        override val message: String = "Failed to save session. Please try again."
    }

    // ============================================================================
    // Authentication Operations (2-200-050 to 2-200-059)
    // ============================================================================

    /**
     * Login failed due to invalid credentials.
     * Error Code: 2-200-050
     */
    @Serializable
    data object LoginInvalidCredentials : SessionErrorCatalog {
        override val errorCode: Int = 2200050
        override val message: String = "Invalid username or password. Please try again."
    }

    /**
     * Login failed - account is locked.
     * Error Code: 2-200-051
     */
    @Serializable
    data object LoginAccountLocked : SessionErrorCatalog {
        override val errorCode: Int = 2200051
        override val message: String = "Your account has been locked. Please contact support."
    }

    /**
     * Login failed - email not verified.
     * Error Code: 2-200-052
     */
    @Serializable
    data object LoginEmailNotVerified : SessionErrorCatalog {
        override val errorCode: Int = 2200052
        override val message: String = "Please verify your email address before logging in."
    }

    /**
     * Login failed due to server error.
     * Error Code: 2-200-053
     */
    @Serializable
    data object LoginServerError : SessionErrorCatalog {
        override val errorCode: Int = 2200053
        override val message: String = "Login failed due to server error. Please try again later."
    }

    /**
     * Login failed due to network error.
     * Error Code: 2-200-054
     */
    @Serializable
    data object LoginNetworkError : SessionErrorCatalog {
        override val errorCode: Int = 2200054
        override val message: String = "Login failed. Please check your connection and try again."
    }

    companion object {
        /**
         * Returns a map of all session error catalog entries indexed by error code.
         * Used by ErrorProvider for O(1) lookup performance.
         */
        fun registry(): Map<Int, ErrorCatalog> = mapOf(
            // Fetch operations
            FetchNetworkUnavailable.errorCode to FetchNetworkUnavailable,
            FetchStorageError.errorCode to FetchStorageError,
            FetchNotFound.errorCode to FetchNotFound,
            FetchInvalidData.errorCode to FetchInvalidData,
            FetchTimeout.errorCode to FetchTimeout,
            FetchUnknown.errorCode to FetchUnknown,
            // Save operations
            SaveStorageError.errorCode to SaveStorageError,
            SaveValidationFailed.errorCode to SaveValidationFailed,
            SaveUnknown.errorCode to SaveUnknown,
            // Authentication operations
            LoginInvalidCredentials.errorCode to LoginInvalidCredentials,
            LoginAccountLocked.errorCode to LoginAccountLocked,
            LoginEmailNotVerified.errorCode to LoginEmailNotVerified,
            LoginServerError.errorCode to LoginServerError,
            LoginNetworkError.errorCode to LoginNetworkError,
        )
    }
}
