package io.blackarrows.errors.catalog.i18n.translations

import io.blackarrows.errors.catalog.i18n.ErrorKeys

/**
 * English translations for all error messages.
 *
 * This is the default fallback language for the error catalog.
 * All error keys must have an English translation.
 */
internal val englishTranslations: Map<String, String> = mapOf(
    // ============================================================================
    // Network Errors (10-XXX)
    // ============================================================================
    ErrorKeys.NETWORK_UNAVAILABLE to "Network is unavailable. Please check your connection.",
    ErrorKeys.NETWORK_DNS_FAILURE to "Unable to reach server. Please check your connection.",
    ErrorKeys.NETWORK_SSL_ERROR to "Secure connection failed. Please try again later.",
    ErrorKeys.NETWORK_CONNECTION_REFUSED to "Unable to connect to server. Please try again later.",
    ErrorKeys.NETWORK_TIMEOUT to "Request timed out. Please try again.",
    ErrorKeys.NETWORK_UNKNOWN to "Network error occurred. Please try again.",

    // ============================================================================
    // Storage Errors (11-XXX)
    // ============================================================================
    ErrorKeys.STORAGE_UNAVAILABLE to "Storage is unavailable. Please try again later.",
    ErrorKeys.STORAGE_INSUFFICIENT_SPACE to "Insufficient storage space. Please free up space and try again.",
    ErrorKeys.STORAGE_DATABASE_CORRUPTED to "Database error detected. Please contact support.",
    ErrorKeys.STORAGE_READ_FAILED to "Failed to read data. Please try again.",
    ErrorKeys.STORAGE_WRITE_FAILED to "Failed to save data. Please try again.",
    ErrorKeys.STORAGE_DELETE_FAILED to "Failed to delete data. Please try again.",
    ErrorKeys.STORAGE_UNKNOWN to "Storage error occurred. Please try again.",

    // ============================================================================
    // Auth Errors (12-XXX)
    // ============================================================================
    ErrorKeys.AUTH_UNAUTHORIZED to "Your session has expired. Please log in again.",
    ErrorKeys.AUTH_TOKEN_EXPIRED to "Your session has expired. Please log in again.",
    ErrorKeys.AUTH_FORBIDDEN to "You don't have permission to access this resource.",
    ErrorKeys.AUTH_REFRESH_TOKEN_INVALID to "Session cannot be refreshed. Please log in again.",
    ErrorKeys.AUTH_UNKNOWN to "Authentication error occurred. Please try again.",

    // ============================================================================
    // Session Errors - Fetch Operations (20-000 to 20-099)
    // ============================================================================
    ErrorKeys.SESSION_FETCH_NETWORK_UNAVAILABLE to "Failed to fetch session. Please check your connection and try again.",
    ErrorKeys.SESSION_FETCH_STORAGE_ERROR to "Failed to fetch session due to storage error. Please try again.",
    ErrorKeys.SESSION_FETCH_NOT_FOUND to "Session not found. Please log in again.",
    ErrorKeys.SESSION_FETCH_INVALID_DATA to "Session data is invalid. Please log in again.",
    ErrorKeys.SESSION_FETCH_TIMEOUT to "Session fetch timed out. Please try again.",
    ErrorKeys.SESSION_FETCH_UNKNOWN to "Failed to fetch session. Please try again.",

    // ============================================================================
    // Session Errors - Login Operations (20-050 to 20-054)
    // ============================================================================
    ErrorKeys.SESSION_LOGIN_INVALID_CREDENTIALS to "Invalid username or password. Please try again.",
    ErrorKeys.SESSION_LOGIN_ACCOUNT_LOCKED to "Your account has been locked. Please contact support.",
    ErrorKeys.SESSION_LOGIN_EMAIL_NOT_VERIFIED to "Please verify your email address before logging in.",
    ErrorKeys.SESSION_LOGIN_SERVER_ERROR to "Login failed due to server error. Please try again later.",
    ErrorKeys.SESSION_LOGIN_NETWORK_ERROR to "Login failed. Please check your connection and try again.",

    // ============================================================================
    // Session Errors - Save Operations (20-100 to 20-199)
    // ============================================================================
    ErrorKeys.SESSION_SAVE_STORAGE_ERROR to "Failed to save session. Please try again.",
    ErrorKeys.SESSION_SAVE_VALIDATION_FAILED to "Session data is invalid. Please check your input.",
    ErrorKeys.SESSION_SAVE_UNKNOWN to "Failed to save session. Please try again.",
)
