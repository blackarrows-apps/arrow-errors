package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Authentication/Authorization infrastructure errors.
 * Context Code: 020
 * Error Code Range: 1-020-000 to 1-020-999
 *
 * These are cross-cutting auth errors that can occur in any feature.
 */
sealed interface AuthErrorCatalog : ErrorCatalog {

    /**
     * User is unauthorized (401) - session expired or invalid token.
     * Error Code: 1-020-000
     */
    @Serializable
    data object Unauthorized : AuthErrorCatalog {
        override val errorCode: Int = 1020000
        override val message: String = "Your session has expired. Please log in again."
    }

    /**
     * Session token has expired.
     * Error Code: 1-020-001
     */
    @Serializable
    data object TokenExpired : AuthErrorCatalog {
        override val errorCode: Int = 1020001
        override val message: String = "Your session has expired. Please log in again."
    }

    /**
     * Access forbidden (403) - user lacks permissions.
     * Error Code: 1-020-002
     */
    @Serializable
    data object Forbidden : AuthErrorCatalog {
        override val errorCode: Int = 1020002
        override val message: String = "You don't have permission to access this resource."
    }

    /**
     * Refresh token is invalid or expired.
     * Error Code: 1-020-003
     */
    @Serializable
    data object RefreshTokenInvalid : AuthErrorCatalog {
        override val errorCode: Int = 1020003
        override val message: String = "Session cannot be refreshed. Please log in again."
    }

    /**
     * Generic auth error (fallback).
     * Error Code: 1-020-999
     */
    @Serializable
    data object Unknown : AuthErrorCatalog {
        override val errorCode: Int = 1020999
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
    }
}
