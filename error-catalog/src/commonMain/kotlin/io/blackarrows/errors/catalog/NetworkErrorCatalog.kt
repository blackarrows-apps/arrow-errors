package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Network-related errors.
 * Category Code: 10
 * Error Code Range: 10-000 to 10-999
 *
 * These errors occur during network operations and are retryable.
 */
sealed interface NetworkErrorCatalog : ErrorCatalog {

    /**
     * Network is completely unavailable (no connection).
     * Error Code: 10-000
     */
    @Serializable
    data object Unavailable : NetworkErrorCatalog {
        override val errorCode: Int = 10000
        override val message: String = "Network is unavailable. Please check your connection."
    }

    /**
     * DNS resolution failed.
     * Error Code: 10-001
     */
    @Serializable
    data object DnsFailure : NetworkErrorCatalog {
        override val errorCode: Int = 10001
        override val message: String = "Unable to reach server. Please check your connection."
    }

    /**
     * SSL/TLS certificate validation failed.
     * Error Code: 10-002
     */
    @Serializable
    data object SslError : NetworkErrorCatalog {
        override val errorCode: Int = 10002
        override val message: String = "Secure connection failed. Please try again later."
    }

    /**
     * Connection refused by server.
     * Error Code: 10-003
     */
    @Serializable
    data object ConnectionRefused : NetworkErrorCatalog {
        override val errorCode: Int = 10003
        override val message: String = "Unable to connect to server. Please try again later."
    }

    /**
     * Network request timed out.
     * Error Code: 10-500
     */
    @Serializable
    data object Timeout : NetworkErrorCatalog {
        override val errorCode: Int = 10500
        override val message: String = "Request timed out. Please try again."
    }

    /**
     * Generic network error (fallback).
     * Error Code: 10-999
     */
    @Serializable
    data object Unknown : NetworkErrorCatalog {
        override val errorCode: Int = 10999
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

        /**
         * Maps error codes to their corresponding message keys for i18n support.
         *
         * This map is used to convert error codes from the catalog into message keys
         * that can be resolved using [io.blackarrows.errors.catalog.i18n.MessageResolver].
         */
        fun messageKeyMapping(): Map<Int, String> = mapOf(
            Unavailable.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.NETWORK_UNAVAILABLE,
            Timeout.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.NETWORK_TIMEOUT,
            DnsFailure.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.NETWORK_DNS_FAILURE,
            SslError.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.NETWORK_SSL_ERROR,
            ConnectionRefused.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.NETWORK_CONNECTION_REFUSED,
            Unknown.errorCode to io.blackarrows.errors.catalog.i18n.ErrorKeys.NETWORK_UNKNOWN,
        )
    }
}
