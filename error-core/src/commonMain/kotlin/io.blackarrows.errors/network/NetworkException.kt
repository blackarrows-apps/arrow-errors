package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when a network connectivity error occurs.
 *
 * This exception is used for network-level failures such as connection timeouts,
 * DNS resolution failures, or loss of network connectivity. It's distinct from
 * HTTP-level errors which are handled by other exception types.
 *
 * Example usage:
 * ```kotlin
 * try {
 *     makeHttpRequest()
 * } catch (e: IOException) {
 *     throw NetworkException(
 *         msg = UiMessage.Plain("Unable to connect. Please check your internet connection."),
 *         error = e
 *     )
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Error)
 * @property presentation How the error should be displayed (default: Snackbar)
 * @property primaryAction Primary action user can take (default: Retry)
 * @property error The underlying exception that caused this error
 */
data class NetworkException(
    override val id: String? = "network_error",
    override val msg: UiMessage = UiMessage.Plain("Network error occurred."),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Snackbar,
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
