package io.blackarrows.errors.base

open class ErrorAction(
    open val actionId: String,
    open val label: UiMessage,
    open val isDestructive: Boolean = false
) {
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
