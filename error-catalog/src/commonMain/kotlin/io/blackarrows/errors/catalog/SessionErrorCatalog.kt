package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Session domain errors.
 * Category Code: 20
 * Error Code Range: 20-000 to 20-999
 *
 * Session-related business logic errors.
 */
sealed interface SessionErrorCatalog : ErrorCatalog {

    // ============================================================================
    // Fetch Operations (20-000 to 20-099)
    // ============================================================================

    /**
     * Failed to fetch session due to network unavailability.
     * Error Code: 20-000
     */
    @Serializable
    data object FetchNetworkUnavailable : SessionErrorCatalog {
        override val errorCode: Int = 20000
        override val message: String = "Failed to fetch session. Please check your connection and try again."
    }

    /**
     * Failed to fetch session due to storage error.
     * Error Code: 20-001
     */
    @Serializable
    data object FetchStorageError : SessionErrorCatalog {
        override val errorCode: Int = 20001
        override val message: String = "Failed to fetch session due to storage error. Please try again."
    }

    /**
     * Session not found.
     * Error Code: 20-002
     */
    @Serializable
    data object FetchNotFound : SessionErrorCatalog {
        override val errorCode: Int = 20002
        override val message: String = "Session not found. Please log in again."
    }

    /**
     * Session data is invalid or corrupted.
     * Error Code: 20-003
     */
    @Serializable
    data object FetchInvalidData : SessionErrorCatalog {
        override val errorCode: Int = 20003
        override val message: String = "Session data is invalid. Please log in again."
    }

    /**
     * Login failed due to invalid credentials.
     * Error Code: 20-050
     */
    @Serializable
    data object LoginInvalidCredentials : SessionErrorCatalog {
        override val errorCode: Int = 20050
        override val message: String = "Invalid username or password. Please try again."
    }

    /**
     * Login failed - account is locked.
     * Error Code: 20-051
     */
    @Serializable
    data object LoginAccountLocked : SessionErrorCatalog {
        override val errorCode: Int = 20051
        override val message: String = "Your account has been locked. Please contact support."
    }

    /**
     * Login failed - email not verified.
     * Error Code: 20-052
     */
    @Serializable
    data object LoginEmailNotVerified : SessionErrorCatalog {
        override val errorCode: Int = 20052
        override val message: String = "Please verify your email address before logging in."
    }

    /**
     * Login failed due to server error.
     * Error Code: 20-053
     */
    @Serializable
    data object LoginServerError : SessionErrorCatalog {
        override val errorCode: Int = 20053
        override val message: String = "Login failed due to server error. Please try again later."
    }

    /**
     * Login failed due to network error.
     * Error Code: 20-054
     */
    @Serializable
    data object LoginNetworkError : SessionErrorCatalog {
        override val errorCode: Int = 20054
        override val message: String = "Login failed. Please check your connection and try again."
    }

    // ============================================================================
    // Save Operations (20-100 to 20-199)
    // ============================================================================

    /**
     * Failed to save session due to storage error.
     * Error Code: 20-100
     */
    @Serializable
    data object SaveStorageError : SessionErrorCatalog {
        override val errorCode: Int = 20100
        override val message: String = "Failed to save session. Please try again."
    }

    /**
     * Unknown save error.
     * Error Code: 20-199
     */
    @Serializable
    data object SaveUnknown : SessionErrorCatalog {
        override val errorCode: Int = 20199
        override val message: String = "Failed to save session. Please try again."
    }

    // ============================================================================
    // Validation & Timeout Errors (20-400 to 20-599)
    // ============================================================================

    /**
     * Session validation failed during save.
     * Error Code: 20-400
     */
    @Serializable
    data object SaveValidationFailed : SessionErrorCatalog {
        override val errorCode: Int = 20400
        override val message: String = "Session data is invalid. Please check your input."
    }

    /**
     * Session fetch timed out.
     * Error Code: 20-500
     */
    @Serializable
    data object FetchTimeout : SessionErrorCatalog {
        override val errorCode: Int = 20500
        override val message: String = "Session fetch timed out. Please try again."
    }

    /**
     * Unknown fetch error.
     * Error Code: 20-999
     */
    @Serializable
    data object FetchUnknown : SessionErrorCatalog {
        override val errorCode: Int = 20999
        override val message: String = "Failed to fetch session. Please try again."
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

        /**
         * Maps error codes to their corresponding message keys for i18n support.
         *
         * This map is used to convert error codes from the catalog into message keys
         * that can be resolved using [io.blackarrows.errors.catalog.i18n.MessageResolver].
         */
        fun messageKeyMapping(): Map<Int, String> = mapOf(
            // Fetch operations
            FetchNetworkUnavailable.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_FETCH_NETWORK_UNAVAILABLE,
            FetchStorageError.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_FETCH_STORAGE_ERROR,
            FetchNotFound.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_FETCH_NOT_FOUND,
            FetchInvalidData.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_FETCH_INVALID_DATA,
            FetchTimeout.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_FETCH_TIMEOUT,
            FetchUnknown.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_FETCH_UNKNOWN,
            // Save operations
            SaveStorageError.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_SAVE_STORAGE_ERROR,
            SaveValidationFailed.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_SAVE_VALIDATION_FAILED,
            SaveUnknown.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_SAVE_UNKNOWN,
            // Login operations
            LoginInvalidCredentials.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_LOGIN_INVALID_CREDENTIALS,
            LoginAccountLocked.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_LOGIN_ACCOUNT_LOCKED,
            LoginEmailNotVerified.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_LOGIN_EMAIL_NOT_VERIFIED,
            LoginServerError.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_LOGIN_SERVER_ERROR,
            LoginNetworkError.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.SESSION_LOGIN_NETWORK_ERROR,
        )
    }
}
