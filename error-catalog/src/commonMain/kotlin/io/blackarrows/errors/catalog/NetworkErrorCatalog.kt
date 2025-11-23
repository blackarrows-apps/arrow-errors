package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Network-related infrastructure errors.
 * Context Code: 001
 * Error Code Range: 1-001-000 to 1-001-999
 *
 * These errors occur during network operations and are retryable.
 */
sealed interface NetworkErrorCatalog : ErrorCatalog {

    /**
     * Network is completely unavailable (no connection).
     * Error Code: 1-001-000
     */
    @Serializable
    data object Unavailable : NetworkErrorCatalog {
        override val errorCode: Int = 1001000
        override val message: String = "Network is unavailable. Please check your connection."
    }

    /**
     * Network request timed out.
     * Error Code: 1-001-500
     */
    @Serializable
    data object Timeout : NetworkErrorCatalog {
        override val errorCode: Int = 1001500
        override val message: String = "Request timed out. Please try again."
    }

    /**
     * DNS resolution failed.
     * Error Code: 1-001-001
     */
    @Serializable
    data object DnsFailure : NetworkErrorCatalog {
        override val errorCode: Int = 1001001
        override val message: String = "Unable to reach server. Please check your connection."
    }

    /**
     * SSL/TLS certificate validation failed.
     * Error Code: 1-001-002
     */
    @Serializable
    data object SslError : NetworkErrorCatalog {
        override val errorCode: Int = 1001002
        override val message: String = "Secure connection failed. Please try again later."
    }

    /**
     * Connection refused by server.
     * Error Code: 1-001-003
     */
    @Serializable
    data object ConnectionRefused : NetworkErrorCatalog {
        override val errorCode: Int = 1001003
        override val message: String = "Unable to connect to server. Please try again later."
    }

    /**
     * Generic network error (fallback).
     * Error Code: 1-001-999
     */
    @Serializable
    data object Unknown : NetworkErrorCatalog {
        override val errorCode: Int = 1001999
        override val message: String = "Network error occurred. Please try again."
    }

    companion object {
        /**
         * Returns a map of all network error catalog entries indexed by error code.
         * Used by ErrorProvider for O(1) lookup performance.
         */
        fun registry(): Map<Int, ErrorCatalog> = mapOf(
            Unavailable.errorCode to Unavailable,
            Timeout.errorCode to Timeout,
            DnsFailure.errorCode to DnsFailure,
            SslError.errorCode to SslError,
            ConnectionRefused.errorCode to ConnectionRefused,
            Unknown.errorCode to Unknown,
        )
    }
}
