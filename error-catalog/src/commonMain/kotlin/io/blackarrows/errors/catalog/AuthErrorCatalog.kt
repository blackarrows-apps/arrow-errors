package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Authentication/Authorization errors.
 * Category Code: 12
 * Error Code Range: 12-000 to 12-999
 *
 * These are cross-cutting auth errors that can occur in any feature.
 */
sealed interface AuthErrorCatalog : ErrorCatalog {

    /**
     * User is unauthorized (401) - session expired or invalid token.
     * Error Code: 12-000
     */
    @Serializable
    data object Unauthorized : AuthErrorCatalog {
        override val errorCode: Int = 12000
        override val message: String = "Your session has expired. Please log in again."
    }

    /**
     * Session token has expired.
     * Error Code: 12-001
     */
    @Serializable
    data object TokenExpired : AuthErrorCatalog {
        override val errorCode: Int = 12001
        override val message: String = "Your session has expired. Please log in again."
    }

    /**
     * Access forbidden (403) - user lacks permissions.
     * Error Code: 12-002
     */
    @Serializable
    data object Forbidden : AuthErrorCatalog {
        override val errorCode: Int = 12002
        override val message: String = "You don't have permission to access this resource."
    }

    /**
     * Refresh token is invalid or expired.
     * Error Code: 12-003
     */
    @Serializable
    data object RefreshTokenInvalid : AuthErrorCatalog {
        override val errorCode: Int = 12003
        override val message: String = "Session cannot be refreshed. Please log in again."
    }

    /**
     * Generic auth error (fallback).
     * Error Code: 12-999
     */
    @Serializable
    data object Unknown : AuthErrorCatalog {
        override val errorCode: Int = 12999
        override val message: String = "Authentication error occurred. Please try again."
    }

    companion object {
        /**
         * Returns a map of all auth error catalog entries indexed by error code.
         * Used by ErrorProvider for O(1) lookup performance.
         */
        fun registry(): Map<Int, ErrorCatalog> = mapOf(
            Unauthorized.errorCode to Unauthorized,
            TokenExpired.errorCode to TokenExpired,
            Forbidden.errorCode to Forbidden,
            RefreshTokenInvalid.errorCode to RefreshTokenInvalid,
            Unknown.errorCode to Unknown,
        )

        /**
         * Maps error codes to their corresponding message keys for i18n support.
         *
         * This map is used to convert error codes from the catalog into message keys
         * that can be resolved using [io.blackarrows.errors.catalog.i18n.MessageResolver].
         */
        fun messageKeyMapping(): Map<Int, String> = mapOf(
            Unauthorized.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.AUTH_UNAUTHORIZED,
            TokenExpired.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.AUTH_TOKEN_EXPIRED,
            Forbidden.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.AUTH_FORBIDDEN,
            RefreshTokenInvalid.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.AUTH_REFRESH_TOKEN_INVALID,
            Unknown.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.AUTH_UNKNOWN,
        )
    }
}
