package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when local storage operations fail.
 *
 * This exception is used for errors related to reading from or writing to
 * local storage, such as database errors, file system issues, or storage
 * capacity problems.
 *
 * Example usage:
 * ```kotlin
 * try {
 *     saveToLocalStorage(data)
 * } catch (e: IOException) {
 *     throw StorageException(error = e)
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
data class StorageException(
    override val id: String? = "storage_error",
    override val msg: UiMessage = UiMessage.Plain("Error, retrieving your details, please check your storage capacity"),
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
