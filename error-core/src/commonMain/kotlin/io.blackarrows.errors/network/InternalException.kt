package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when a server-side internal error occurs (5xx status codes).
 *
 * This exception represents server-side failures that are beyond the client's
 * control, such as internal server errors (500), bad gateway (502), or
 * service unavailable (503) responses.
 *
 * Example usage:
 * ```kotlin
 * when (response.status) {
 *     500 -> throw InternalException(
 *         msg = UiMessage.Plain("Server error. Please try again later.")
 *     )
 *     503 -> throw InternalException(
 *         msg = UiMessage.Plain("Service temporarily unavailable")
 *     )
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Critical)
 * @property presentation How the error should be displayed (default: Dialog)
 * @property error The underlying exception that caused this error
 */
data class InternalException(
    override val id: String? = "internal_error",
    override val msg: UiMessage = UiMessage.Plain("Internal error."),
    override val severity: ErrorSeverity = ErrorSeverity.Critical,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    error = error
)
