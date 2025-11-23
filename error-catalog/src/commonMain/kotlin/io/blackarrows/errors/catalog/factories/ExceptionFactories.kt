package io.blackarrows.errors.catalog.factories

import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage
import io.blackarrows.errors.catalog.i18n.ErrorKeys
import io.blackarrows.errors.network.AuthException
import io.blackarrows.errors.network.NetworkException
import io.blackarrows.errors.network.StorageException
import io.blackarrows.errors.session.SessionFailedException

/**
 * Factory functions for creating exceptions with i18n support.
 *
 * These factory functions provide convenient ways to create exception instances
 * with sensible defaults while allowing complete customization of all parameters.
 *
 * ## Key Features
 * - Default messages use [UiMessage.ResourceId] for i18n support
 * - All parameters are overridable for complete flexibility
 * - Sensible defaults based on exception type
 * - Type-safe and IDE-friendly with parameter names
 *
 * ## Usage Patterns
 *
 * ### Simple (Use defaults)
 * ```kotlin
 * throw networkException(error = IOException("Connection failed"))
 * ```
 *
 * ### Override message
 * ```kotlin
 * throw networkException(
 *     msg = UiMessage.Plain("Custom network error"),
 *     error = IOException()
 * )
 * ```
 *
 * ### Override actions and navigation
 * ```kotlin
 * throw authException(
 *     primaryAction = ErrorAction.Dismiss,
 *     navigation = CustomNavigation.Onboarding,
 *     error = authError
 * )
 * ```
 *
 * ### Override everything
 * ```kotlin
 * throw networkException(
 *     id = "custom_network_error",
 *     msg = UiMessage.Plain("Completely custom"),
 *     severity = ErrorSeverity.Warning,
 *     presentation = ErrorPresentation.Silent,
 *     primaryAction = CustomAction.ContactSupport,
 *     error = error
 * )
 * ```
 */

// ============================================================================
// Network Exceptions
// ============================================================================

/**
 * Creates a [NetworkException] with i18n support.
 *
 * Default behavior:
 * - Message: [ErrorKeys.NETWORK_UNKNOWN] (i18n)
 * - Severity: [ErrorSeverity.Error]
 * - Presentation: [ErrorPresentation.Snackbar]
 * - Primary Action: [ErrorAction.Retry]
 *
 * @param id Unique identifier for this error instance
 * @param msg User-facing message (defaults to i18n key)
 * @param severity Severity level
 * @param presentation How to display the error
 * @param primaryAction Primary action user can take
 * @param error Underlying cause
 * @return NetworkException instance
 */
fun networkException(
    id: String? = "network_error",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Snackbar,
    primaryAction: ErrorAction? = ErrorAction.Retry,
    error: Throwable? = null,
) = NetworkException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.NETWORK_UNKNOWN),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)

/**
 * Creates a [NetworkException] for network unavailable errors.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.NETWORK_UNAVAILABLE])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param error Underlying cause
 * @return NetworkException instance
 */
fun networkUnavailableException(
    id: String? = "network_unavailable",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Snackbar,
    primaryAction: ErrorAction? = ErrorAction.Retry,
    error: Throwable? = null,
) = NetworkException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.NETWORK_UNAVAILABLE),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)

/**
 * Creates a [NetworkException] for timeout errors.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.NETWORK_TIMEOUT])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param error Underlying cause
 * @return NetworkException instance
 */
fun networkTimeoutException(
    id: String? = "network_timeout",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Snackbar,
    primaryAction: ErrorAction? = ErrorAction.Retry,
    error: Throwable? = null,
) = NetworkException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.NETWORK_TIMEOUT),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)

// ============================================================================
// Auth Exceptions
// ============================================================================

/**
 * Creates an [AuthException] with i18n support.
 *
 * Default behavior:
 * - Message: [ErrorKeys.AUTH_UNKNOWN] (i18n)
 * - Severity: [ErrorSeverity.Error]
 * - Presentation: [ErrorPresentation.Dialog]
 * - Primary Action: [ErrorAction.Retry]
 * - Navigation: [ErrorNavigation.Login]
 *
 * @param id Unique identifier for this error instance
 * @param msg User-facing message (defaults to i18n key)
 * @param severity Severity level
 * @param presentation How to display the error
 * @param primaryAction Primary action user can take
 * @param secondaryAction Optional secondary action
 * @param navigation Navigation target after acknowledgment
 * @param error Underlying cause
 * @return AuthException instance
 */
fun authException(
    id: String? = "auth_error",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Retry,
    secondaryAction: ErrorAction? = null,
    navigation: ErrorNavigation? = ErrorNavigation.Login,
    error: Throwable? = null,
) = AuthException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.AUTH_UNKNOWN),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    secondaryAction = secondaryAction,
    navigation = navigation,
    error = error
)

/**
 * Creates an [AuthException] for unauthorized (401) errors.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.AUTH_UNAUTHORIZED])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param secondaryAction Secondary action
 * @param navigation Navigation target
 * @param error Underlying cause
 * @return AuthException instance
 */
fun unauthorizedException(
    id: String? = "auth_unauthorized",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Dismiss,
    secondaryAction: ErrorAction? = null,
    navigation: ErrorNavigation? = ErrorNavigation.Login,
    error: Throwable? = null,
) = AuthException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.AUTH_UNAUTHORIZED),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    secondaryAction = secondaryAction,
    navigation = navigation,
    error = error
)

/**
 * Creates an [AuthException] for token expired errors.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.AUTH_TOKEN_EXPIRED])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param secondaryAction Secondary action
 * @param navigation Navigation target
 * @param error Underlying cause
 * @return AuthException instance
 */
fun tokenExpiredException(
    id: String? = "auth_token_expired",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Dismiss,
    secondaryAction: ErrorAction? = null,
    navigation: ErrorNavigation? = ErrorNavigation.Login,
    error: Throwable? = null,
) = AuthException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.AUTH_TOKEN_EXPIRED),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    secondaryAction = secondaryAction,
    navigation = navigation,
    error = error
)

/**
 * Creates an [AuthException] for forbidden (403) errors.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.AUTH_FORBIDDEN])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param secondaryAction Secondary action
 * @param navigation Navigation target
 * @param error Underlying cause
 * @return AuthException instance
 */
fun forbiddenException(
    id: String? = "auth_forbidden",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Dismiss,
    secondaryAction: ErrorAction? = null,
    navigation: ErrorNavigation? = ErrorNavigation.Back,
    error: Throwable? = null,
) = AuthException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.AUTH_FORBIDDEN),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    secondaryAction = secondaryAction,
    navigation = navigation,
    error = error
)

// ============================================================================
// Storage Exceptions
// ============================================================================

/**
 * Creates a [StorageException] with i18n support.
 *
 * Default behavior:
 * - Message: [ErrorKeys.STORAGE_UNKNOWN] (i18n)
 * - Severity: [ErrorSeverity.Error]
 * - Presentation: [ErrorPresentation.Dialog]
 * - Primary Action: [ErrorAction.Retry]
 *
 * @param id Unique identifier for this error instance
 * @param msg User-facing message (defaults to i18n key)
 * @param severity Severity level
 * @param presentation How to display the error
 * @param primaryAction Primary action user can take
 * @param error Underlying cause
 * @return StorageException instance
 */
fun storageException(
    id: String? = "storage_error",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Retry,
    error: Throwable? = null,
) = StorageException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.STORAGE_UNKNOWN),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)

/**
 * Creates a [StorageException] for storage unavailable errors.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.STORAGE_UNAVAILABLE])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param error Underlying cause
 * @return StorageException instance
 */
fun storageUnavailableException(
    id: String? = "storage_unavailable",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Retry,
    error: Throwable? = null,
) = StorageException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.STORAGE_UNAVAILABLE),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)

/**
 * Creates a [StorageException] for insufficient space errors.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.STORAGE_INSUFFICIENT_SPACE])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param error Underlying cause
 * @return StorageException instance
 */
fun insufficientSpaceException(
    id: String? = "storage_insufficient_space",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Error,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Dismiss,
    error: Throwable? = null,
) = StorageException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.STORAGE_INSUFFICIENT_SPACE),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)

// ============================================================================
// Session Exceptions
// ============================================================================

/**
 * Creates a [SessionFailedException] with i18n support.
 *
 * Default behavior:
 * - Message: [ErrorKeys.SESSION_FETCH_NOT_FOUND] (i18n)
 * - Severity: [ErrorSeverity.Critical]
 * - Presentation: [ErrorPresentation.Dialog]
 * - Primary Action: [ErrorAction.Dismiss]
 * - Navigation: [ErrorNavigation.Login]
 *
 * @param id Unique identifier for this error instance
 * @param msg User-facing message (defaults to i18n key)
 * @param severity Severity level
 * @param presentation How to display the error
 * @param primaryAction Primary action user can take
 * @param navigation Navigation target after acknowledgment
 * @param error Underlying cause
 * @return SessionFailedException instance
 */
fun sessionFailedException(
    id: String? = "session_failed",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Critical,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Dismiss,
    navigation: ErrorNavigation? = ErrorNavigation.Login,
    error: Throwable? = null,
) = SessionFailedException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.SESSION_FETCH_NOT_FOUND),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    navigation = navigation,
    error = error
)

/**
 * Creates a [SessionFailedException] for invalid session data.
 *
 * @param id Unique identifier
 * @param msg Custom message (defaults to [ErrorKeys.SESSION_FETCH_INVALID_DATA])
 * @param severity Severity level
 * @param presentation How to display
 * @param primaryAction Primary action
 * @param navigation Navigation target
 * @param error Underlying cause
 * @return SessionFailedException instance
 */
fun invalidSessionException(
    id: String? = "session_invalid_data",
    msg: UiMessage? = null,
    severity: ErrorSeverity = ErrorSeverity.Critical,
    presentation: ErrorPresentation = ErrorPresentation.Dialog,
    primaryAction: ErrorAction? = ErrorAction.Dismiss,
    navigation: ErrorNavigation? = ErrorNavigation.Login,
    error: Throwable? = null,
) = SessionFailedException(
    id = id,
    msg = msg ?: UiMessage.ResourceId(ErrorKeys.SESSION_FETCH_INVALID_DATA),
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    navigation = navigation,
    error = error
)
