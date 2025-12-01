package io.blackarrows.errors.base

/**
 * Represents an action that can be taken in response to an error.
 *
 * This open class provides common error actions with predefined actions in the companion object.
 * Applications can use the [Custom] subclass to create app-specific actions.
 *
 * Example usage:
 * ```kotlin
 * // Using predefined actions
 * throw NetworkException(
 *     primaryAction = ErrorAction.Retry,
 *     secondaryAction = ErrorAction.Dismiss
 * )
 *
 * // Using custom actions
 * throw VideoException(
 *     primaryAction = ErrorAction.Custom(
 *         actionId = "skip_video",
 *         label = UiMessage.Plain("Skip Video")
 *     )
 * )
 * ```
 *
 * @property actionId Unique identifier for this action
 * @property label The user-facing label for this action
 * @property isDestructive Whether this action is destructive (e.g., delete, remove)
 */
open class ErrorAction(
    open val actionId: String,
    open val label: UiMessage,
    open val isDestructive: Boolean = false
) {
    /**
     * Custom error action for app-specific use cases.
     *
     * Use this to define actions specific to your application that aren't covered
     * by the predefined actions.
     *
     * Example:
     * ```kotlin
     * ErrorAction.Custom(
     *     actionId = "view_details",
     *     label = UiMessage.ResourceId("action_view_details"),
     *     isDestructive = false
     * )
     * ```
     *
     * @property actionId Unique identifier for this custom action
     * @property label The user-facing label for this action
     * @property isDestructive Whether this action is destructive (defaults to false)
     */
    data class Custom(
        override val actionId: String,
        override val label: UiMessage,
        override val isDestructive: Boolean = false
    ) : ErrorAction(actionId, label, isDestructive)

    companion object {
        val Retry = ErrorAction(
            actionId = "retry",
            label = UiMessage.Plain("Retry"),
            isDestructive = false
        )
        val Dismiss = ErrorAction(
            actionId = "dismiss",
            label = UiMessage.Plain("Dismiss"),
            isDestructive = false
        )
        val Cancel = ErrorAction(
            actionId = "cancel",
            label = UiMessage.Plain("Cancel"),
            isDestructive = false
        )
        val Close = ErrorAction(
            actionId = "close",
            label = UiMessage.Plain("Close"),
            isDestructive = false
        )
        val Ok = ErrorAction(
            actionId = "ok",
            label = UiMessage.Plain("OK"),
            isDestructive = false
        )
        val Confirm = ErrorAction(
            actionId = "confirm",
            label = UiMessage.Plain("Confirm"),
            isDestructive = false
        )
        val Delete = ErrorAction(
            actionId = "delete",
            label = UiMessage.Plain("Delete"),
            isDestructive = true
        )
        val Remove = ErrorAction(
            actionId = "remove",
            label = UiMessage.Plain("Remove"),
            isDestructive = true
        )
    }
}
