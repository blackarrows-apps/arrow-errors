package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when authentication fails.
 *
 * This exception is used for authentication-related errors such as invalid
 * credentials, expired tokens, or unauthorized access attempts. It typically
 * suggests navigation to the login screen.
 *
 * Example usage:
 * ```kotlin
 * if (response.status == 401) {
 *     throw AuthException(
 *         msg = UiMessage.Plain("Your session has expired. Please log in again."),
 *         navigation = ErrorNavigation.Login
 *     )
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Error)
 * @property presentation How the error should be displayed (default: Dialog)
 * @property primaryAction Primary action user can take (default: Retry)
 * @property secondaryAction Optional secondary action
 * @property navigation Navigation target after error acknowledgment (default: Login)
 * @property error The underlying exception that caused this error
 */
data class AuthException(
    override val id: String? = "auth_error",
    override val msg: UiMessage = UiMessage.Plain("Authentication error."),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val secondaryAction: ErrorAction? = null,
    override val navigation: ErrorNavigation? = ErrorNavigation.Login,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    secondaryAction = secondaryAction,
    navigation = navigation,
    error = error
)
