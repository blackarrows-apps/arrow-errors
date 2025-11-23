package io.blackarrows.errors

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

/**
 * Exception thrown when an operation requires data but the list is empty.
 *
 * This warning-level exception is used when a user attempts to perform an
 * operation that requires at least one item in a list, but the list is empty.
 * It's commonly used in UI scenarios where actions depend on having data available.
 *
 * Example usage:
 * ```kotlin
 * fun processItems(items: List<Item>) {
 *     if (items.isEmpty()) {
 *         throw EmptyListException(
 *             msg = UiMessage.Plain("No items available. Please add items first.")
 *         )
 *     }
 *     // Process items...
 * }
 * ```
 *
 * @property id Unique identifier for this error type
 * @property msg User-facing message describing the error
 * @property severity Severity level of the error (default: Warning)
 * @property presentation How the error should be displayed (default: Dialog)
 * @property primaryAction Primary action user can take (default: Retry)
 * @property navigation Navigation target after error acknowledgment (default: Back)
 * @property error The underlying exception that caused this error
 */
data class EmptyListException(
    override val id: String? = "empty_list",
    override val msg: UiMessage = UiMessage.Plain("The list is empty, please add some data and try again."),
    override val severity: ErrorSeverity = ErrorSeverity.Warning,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val navigation: ErrorNavigation? = ErrorNavigation.Back,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    navigation = navigation,
    error = error
)
