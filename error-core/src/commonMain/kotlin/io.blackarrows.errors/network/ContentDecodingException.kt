package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when content decoding fails.
 *
 * This exception is used when the response content cannot be decoded properly,
 * such as when dealing with compressed content (gzip, deflate) or encoding
 * issues that prevent proper interpretation of the response body.
 *
 * Example usage:
 * ```kotlin
 * try {
 *     decompressGzipContent(response.body)
 * } catch (e: Exception) {
 *     throw ContentDecodingException(
 *         msg = UiMessage.Plain("Failed to decode compressed response"),
 *         error = e
 *     )
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Error)
 * @property presentation How the error should be displayed (default: Dialog)
 * @property primaryAction Primary action user can take (default: Dismiss)
 * @property error The underlying exception that caused this error
 */
data class ContentDecodingException(
    override val id: String? = "content_decoding_error",
    override val msg: UiMessage = UiMessage.Plain("Error decoding content."),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Dismiss,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)
