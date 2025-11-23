package io.blackarrows.errors.base

/**
 * Represents a UI message that can be displayed to the user in various formats.
 *
 * This sealed interface provides a flexible way to define user-facing messages,
 * supporting plain text, resource-based strings (for localization), and formatted
 * messages with dynamic arguments.
 *
 * Example usage:
 * ```kotlin
 * // Plain text message
 * val plain = UiMessage.Plain("Network error occurred")
 *
 * // Resource-based message for localization
 * val resource = UiMessage.ResourceId("error_network")
 *
 * // Formatted message with arguments
 * val formatted = UiMessage.Formatted(
 *     template = "Failed to load {0} items",
 *     args = listOf(5)
 * )
 * ```
 */
sealed interface UiMessage {
    /**
     * A plain text message.
     *
     * Use this for simple, hardcoded messages that don't require localization
     * or dynamic content.
     *
     * @property text The message text to display
     */
    data class Plain(val text: String) : UiMessage

    /**
     * A message represented by a resource identifier.
     *
     * Use this for localized messages where the actual text is stored
     * in platform-specific resource files (e.g., strings.xml on Android).
     *
     * @property resId The resource identifier string
     */
    data class ResourceId(val resId: String) : UiMessage

    /**
     * A formatted message with dynamic arguments.
     *
     * Use this for messages that contain placeholders to be replaced
     * with runtime values.
     *
     * @property template The message template with placeholders (e.g., "{0}", "{1}")
     * @property args The list of arguments to substitute into the template
     */
    data class Formatted(val template: String, val args: List<Any>) : UiMessage
}
