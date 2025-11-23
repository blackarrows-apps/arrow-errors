package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when an HTTP response has an empty body when content was expected.
 *
 * This exception is used when the server returns a successful status code but
 * the response body is empty or missing when the client expected data. This
 * is different from a 204 No Content response which explicitly indicates no body.
 *
 * Example usage:
 * ```kotlin
 * val response = httpClient.get("/api/data")
 * if (response.status.isSuccess() && response.body.isEmpty()) {
 *     throw EmptyResponseException(
 *         msg = UiMessage.Plain("Server returned no data")
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
data class EmptyResponseException(
    override val id: String? = "empty_response",
    override val msg: UiMessage = UiMessage.Plain("The response body was empty."),
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
