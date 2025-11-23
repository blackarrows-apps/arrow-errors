package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when JSON parsing or deserialization fails.
 *
 * This exception is used when the response contains invalid JSON or when
 * the JSON structure doesn't match the expected data model. Common causes
 * include malformed JSON, missing required fields, or type mismatches.
 *
 * Example usage:
 * ```kotlin
 * try {
 *     Json.decodeFromString<User>(responseBody)
 * } catch (e: SerializationException) {
 *     throw JsonDecodingException(
 *         msg = UiMessage.Plain("Failed to parse server response"),
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
data class JsonDecodingException(
    override val id: String? = "json_decoding_error",
    override val msg: UiMessage = UiMessage.Plain("Error decoding data."),
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
