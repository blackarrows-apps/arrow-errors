package io.blackarrows.errors.compose.utils

import androidx.compose.runtime.Composable
import io.blackarrows.errors.base.UiMessage
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.i18n.MessageResolver

/**
 * Resolves a [UiMessage] to a display string.
 *
 * This function handles all three types of [UiMessage]:
 * - [UiMessage.Plain]: Returns the text directly
 * - [UiMessage.ResourceId]: Resolves using the provided [MessageResolver]
 * - [UiMessage.Formatted]: Resolves with arguments using the provided [MessageResolver]
 *
 * Use this helper in your custom error components to properly handle i18n messages.
 *
 * Example:
 * ```kotlin
 * @Composable
 * fun MyCustomErrorDialog(error: ActionableException) {
 *     val message = resolveMessage(error.msg)
 *     Text(text = message)
 * }
 * ```
 *
 * @param message The message to resolve
 * @param resolver The message resolver to use (defaults to [DefaultMessageResolver])
 * @return The resolved message string
 */
@Composable
fun resolveMessage(
    message: UiMessage,
    resolver: MessageResolver = DefaultMessageResolver
): String = when (message) {
    is UiMessage.Plain -> message.text
    is UiMessage.ResourceId -> resolver.resolve(message.resId)
    is UiMessage.Formatted -> resolver.resolve(message.template, message.args)
}
