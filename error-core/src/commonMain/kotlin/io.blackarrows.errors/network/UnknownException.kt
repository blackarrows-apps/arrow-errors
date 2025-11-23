package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when an unrecognized or unexpected error occurs.
 *
 * This is a catch-all exception for errors that don't fit into any other
 * exception category. It should be used sparingly and primarily for wrapping
 * unexpected exceptions that need to be surfaced to the user.
 *
 * Example usage:
 * ```kotlin
 * try {
 *     performOperation()
 * } catch (e: Exception) {
 *     // Log the unexpected error for debugging
 *     logger.error("Unexpected error", e)
 *     throw UnknownException(
 *         msg = UiMessage.Plain("An unexpected error occurred"),
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
data class UnknownException(
    override val id: String? = "unknown_error",
    override val msg: UiMessage = UiMessage.Plain("An unknown error occurred."),
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
