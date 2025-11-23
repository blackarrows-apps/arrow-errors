package io.blackarrows.errors.base

open class ErrorAction(
    open val label: UiMessage,
    open val isDestructive: Boolean = false
) {
    companion object {
        val Retry = ErrorAction(
            label = UiMessage.Plain("Retry"),
            isDestructive = false
        )
        val Dismiss = ErrorAction(
            label = UiMessage.Plain("Dismiss"),
            isDestructive = false
        )
        val Cancel = ErrorAction(
            label = UiMessage.Plain("Cancel"),
            isDestructive = false
        )
        val Close = ErrorAction(
            label = UiMessage.Plain("Close"),
            isDestructive = false
        )
        val Ok = ErrorAction(
            label = UiMessage.Plain("OK"),
            isDestructive = false
        )
        val Confirm = ErrorAction(
            label = UiMessage.Plain("Confirm"),
            isDestructive = false
        )
        val Delete = ErrorAction(
            label = UiMessage.Plain("Delete"),
            isDestructive = true
        )
        val Remove = ErrorAction(
            label = UiMessage.Plain("Remove"),
            isDestructive = true
        )
    }
}
