package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when a client-side HTTP error occurs (4xx status codes).
 *
 * This exception represents errors where the client's request was invalid,
 * malformed, or violated server constraints. Common cases include bad
 * requests (400), forbidden access (403), or not found (404) errors.
 *
 * Example usage:
 * ```kotlin
 * when (response.status) {
 *     400 -> throw ClientException(msg = UiMessage.Plain("Invalid request"))
 *     403 -> throw ClientException(msg = UiMessage.Plain("Access forbidden"))
 *     404 -> throw ClientException(msg = UiMessage.Plain("Resource not found"))
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Error)
 * @property presentation How the error should be displayed (default: Dialog)
 * @property primaryAction Primary action user can take (default: Retry)
 * @property secondaryAction Optional secondary action
 * @property navigation Navigation target after error acknowledgment (default: Settings)
 * @property error The underlying exception that caused this error
 */
data class ClientException(
    override val id: String? = "client_error",
    override val msg: UiMessage = UiMessage.Plain("Client error."),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val secondaryAction: ErrorAction? = null,
    override val navigation: ErrorNavigation? = ErrorNavigation.Settings,
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
