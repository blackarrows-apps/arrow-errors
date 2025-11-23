package io.blackarrows.errors.session

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when session information cannot be found or is invalid.
 *
 * This critical exception is used when the user's session has expired,
 * been invalidated, or cannot be retrieved. It typically requires the user
 * to re-authenticate and navigate back to the login screen.
 *
 * Example usage:
 * ```kotlin
 * val session = sessionManager.getCurrentSession()
 * if (session == null || session.isExpired()) {
 *     throw SessionFailedException(
 *         msg = UiMessage.Plain("Your session has expired. Please log in again.")
 *     )
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Critical)
 * @property presentation How the error should be displayed (default: Dialog)
 * @property primaryAction Primary action user can take (default: Dismiss)
 * @property navigation Navigation target after error acknowledgment (default: Login)
 * @property error The underlying exception that caused this error
 */
data class SessionFailedException(
    override val id: String? = "session_failed",
    override val msg: UiMessage = UiMessage.Plain("Session details could not be found, please log in again."),
    override val severity: ErrorSeverity = ErrorSeverity.Critical,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Dismiss,
    override val navigation: ErrorNavigation? = ErrorNavigation.Login,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    navigation = navigation,
    error = error
)
