package io.blackarrows.errors.catalog.i18n

/**
 * Message keys for all error messages in the error catalog.
 *
 * These keys are used with [MessageResolver] to retrieve localized error messages.
 * Each key corresponds to a specific error message that can be translated into multiple languages.
 *
 * Key naming convention: `{category}_{error_type}_{specific_detail}`
 * - category: network, storage, auth, session
 * - error_type: specific error classification
 * - specific_detail: additional context if needed
 *
 * @see MessageResolver
 * @see DefaultMessageResolver
 */
object ErrorKeys {

    // ============================================================================
    // Network Errors (10-XXX)
    // ============================================================================

    /**
     * Network is completely unavailable (no connection).
     * Error Code: 10-000
     */
    const val NETWORK_UNAVAILABLE = "network.unavailable"

    /**
     * DNS resolution failed.
     * Error Code: 10-001
     */
    const val NETWORK_DNS_FAILURE = "network.dns_failure"

    /**
     * SSL/TLS certificate validation failed.
     * Error Code: 10-002
     */
    const val NETWORK_SSL_ERROR = "network.ssl_error"

    /**
     * Connection refused by server.
     * Error Code: 10-003
     */
    const val NETWORK_CONNECTION_REFUSED = "network.connection_refused"

    /**
     * Network request timed out.
     * Error Code: 10-500
     */
    const val NETWORK_TIMEOUT = "network.timeout"

    /**
     * Generic network error (fallback).
     * Error Code: 10-999
     */
    const val NETWORK_UNKNOWN = "network.unknown"

    // ============================================================================
    // Storage Errors (11-XXX)
    // ============================================================================

    /**
     * Storage is completely unavailable.
     * Error Code: 11-000
     */
    const val STORAGE_UNAVAILABLE = "storage.unavailable"

    /**
     * Insufficient storage space.
     * Error Code: 11-001
     */
    const val STORAGE_INSUFFICIENT_SPACE = "storage.insufficient_space"

    /**
     * Database corruption detected.
     * Error Code: 11-002
     */
    const val STORAGE_DATABASE_CORRUPTED = "storage.database_corrupted"

    /**
     * Read operation failed.
     * Error Code: 11-100
     */
    const val STORAGE_READ_FAILED = "storage.read_failed"

    /**
     * Write operation failed.
     * Error Code: 11-200
     */
    const val STORAGE_WRITE_FAILED = "storage.write_failed"

    /**
     * Delete operation failed.
     * Error Code: 11-300
     */
    const val STORAGE_DELETE_FAILED = "storage.delete_failed"

    /**
     * Generic storage error (fallback).
     * Error Code: 11-999
     */
    const val STORAGE_UNKNOWN = "storage.unknown"

    // ============================================================================
    // Auth Errors (12-XXX)
    // ============================================================================

    /**
     * User is unauthorized (401) - session expired or invalid token.
     * Error Code: 12-000
     */
    const val AUTH_UNAUTHORIZED = "auth.unauthorized"

    /**
     * Session token has expired.
     * Error Code: 12-001
     */
    const val AUTH_TOKEN_EXPIRED = "auth.token_expired"

    /**
     * Access forbidden (403) - user lacks permissions.
     * Error Code: 12-002
     */
    const val AUTH_FORBIDDEN = "auth.forbidden"

    /**
     * Refresh token is invalid or expired.
     * Error Code: 12-003
     */
    const val AUTH_REFRESH_TOKEN_INVALID = "auth.refresh_token_invalid"

    /**
     * Generic auth error (fallback).
     * Error Code: 12-999
     */
    const val AUTH_UNKNOWN = "auth.unknown"

    // ============================================================================
    // Session Errors - Fetch Operations (20-000 to 20-099)
    // ============================================================================

    /**
     * Failed to fetch session due to network unavailability.
     * Error Code: 20-000
     */
    const val SESSION_FETCH_NETWORK_UNAVAILABLE = "session.fetch.network_unavailable"

    /**
     * Failed to fetch session due to storage error.
     * Error Code: 20-001
     */
    const val SESSION_FETCH_STORAGE_ERROR = "session.fetch.storage_error"

    /**
     * Session not found.
     * Error Code: 20-002
     */
    const val SESSION_FETCH_NOT_FOUND = "session.fetch.not_found"

    /**
     * Session data is invalid or corrupted.
     * Error Code: 20-003
     */
    const val SESSION_FETCH_INVALID_DATA = "session.fetch.invalid_data"

    /**
     * Session fetch timed out.
     * Error Code: 20-500
     */
    const val SESSION_FETCH_TIMEOUT = "session.fetch.timeout"

    /**
     * Unknown fetch error.
     * Error Code: 20-999
     */
    const val SESSION_FETCH_UNKNOWN = "session.fetch.unknown"

    // ============================================================================
    // Session Errors - Login Operations (20-050 to 20-054)
    // ============================================================================

    /**
     * Login failed due to invalid credentials.
     * Error Code: 20-050
     */
    const val SESSION_LOGIN_INVALID_CREDENTIALS = "session.login.invalid_credentials"

    /**
     * Login failed - account is locked.
     * Error Code: 20-051
     */
    const val SESSION_LOGIN_ACCOUNT_LOCKED = "session.login.account_locked"

    /**
     * Login failed - email not verified.
     * Error Code: 20-052
     */
    const val SESSION_LOGIN_EMAIL_NOT_VERIFIED = "session.login.email_not_verified"

    /**
     * Login failed due to server error.
     * Error Code: 20-053
     */
    const val SESSION_LOGIN_SERVER_ERROR = "session.login.server_error"

    /**
     * Login failed due to network error.
     * Error Code: 20-054
     */
    const val SESSION_LOGIN_NETWORK_ERROR = "session.login.network_error"

    // ============================================================================
    // Session Errors - Save Operations (20-100 to 20-199)
    // ============================================================================

    /**
     * Failed to save session due to storage error.
     * Error Code: 20-100
     */
    const val SESSION_SAVE_STORAGE_ERROR = "session.save.storage_error"

    /**
     * Session validation failed during save.
     * Error Code: 20-400
     */
    const val SESSION_SAVE_VALIDATION_FAILED = "session.save.validation_failed"

    /**
     * Unknown save error.
     * Error Code: 20-199
     */
    const val SESSION_SAVE_UNKNOWN = "session.save.unknown"
}
