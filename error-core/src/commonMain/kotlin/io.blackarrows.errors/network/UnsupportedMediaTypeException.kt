package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when the response has an unsupported or missing Content-Type.
 *
 * This exception is used when the server returns a response with a Content-Type
 * header that the client cannot process, or when the Content-Type header is
 * missing but required for proper handling of the response.
 *
 * Example usage:
 * ```kotlin
 * val contentType = response.headers["Content-Type"]
 * if (contentType == null || !supportedTypes.contains(contentType)) {
 *     throw UnsupportedMediaTypeException(
 *         msg = UiMessage.Plain("Server returned unsupported content type: $contentType")
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
data class UnsupportedMediaTypeException(
    override val id: String? = "unsupported_media_type",
    override val msg: UiMessage = UiMessage.Plain("Unsupported or missing data Content-Type"),
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
