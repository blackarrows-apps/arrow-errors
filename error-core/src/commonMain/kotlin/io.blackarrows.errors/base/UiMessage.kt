package io.blackarrows.errors.base

sealed interface UiMessage {
    data class Plain(val text: String) : UiMessage
    data class ResourceId(val resId: String) : UiMessage
    data class Formatted(val template: String, val args: List<Any>) : UiMessage
}
