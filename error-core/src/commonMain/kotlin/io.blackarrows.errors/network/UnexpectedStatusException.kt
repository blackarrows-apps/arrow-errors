package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when an HTTP response has an unexpected status code.
 *
 * This exception is used as a catch-all for HTTP status codes that don't
 * fit into other specific exception categories or when the application
 * receives a status code it doesn't know how to handle.
 *
 * Example usage:
 * ```kotlin
 * when (response.status.value) {
 *     200 -> processSuccess(response)
 *     401 -> throw AuthException()
 *     404 -> throw ClientException()
 *     else -> throw UnexpectedStatusException(
 *         msg = UiMessage.Plain("Unexpected response: ${response.status}")
 *     )
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Error)
 * @property presentation How the error should be displayed (default: Dialog)
 * @property primaryAction Primary action user can take (default: Retry)
 * @property error The underlying exception that caused this error
 */
data class UnexpectedStatusException(
    override val id: String? = "unexpected_status",
    override val msg: UiMessage = UiMessage.Plain("An unexpected status was received."),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)
