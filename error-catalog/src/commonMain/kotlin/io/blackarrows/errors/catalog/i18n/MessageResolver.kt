package io.blackarrows.errors.catalog.i18n

/**
 * Interface for resolving localized error messages from message keys.
 *
 * Implementations can provide translations from different sources:
 * - Built-in translation maps ([DefaultMessageResolver])
 * - Android Resources (e.g., `context.getString(resId)`)
 * - iOS/Compose Resources
 * - Custom i18n frameworks
 *
 * ## Usage
 *
 * ### With DefaultMessageResolver (Built-in Translations)
 * ```kotlin
 * // Set the locale once during app initialization
 * DefaultMessageResolver.setLocale("es")  // Spanish
 *
 * // Resolve messages
 * val message = DefaultMessageResolver.resolve(ErrorKeys.NETWORK_UNAVAILABLE)
 * // Returns: "La red no está disponible. Por favor, verifica tu conexión."
 * ```
 *
 * ### With Custom Resolver (Android Resources Example)
 * ```kotlin
 * class AndroidResourceResolver(private val context: Context) : MessageResolver {
 *     override fun resolve(key: String): String {
 *         val resId = context.resources.getIdentifier(key, "string", context.packageName)
 *         return if (resId != 0) {
 *             context.getString(resId)
 *         } else {
 *             // Fallback to DefaultMessageResolver
 *             DefaultMessageResolver.resolve(key)
 *         }
 *     }
 *
 *     override fun resolve(key: String, args: List<Any>): String {
 *         val resId = context.resources.getIdentifier(key, "string", context.packageName)
 *         return if (resId != 0) {
 *             context.getString(resId, *args.toTypedArray())
 *         } else {
 *             DefaultMessageResolver.resolve(key, args)
 *         }
 *     }
 * }
 * ```
 *
 * @see DefaultMessageResolver
 * @see ErrorKeys
 */
interface MessageResolver {

    /**
     * Resolves a message for the given key.
     *
     * @param key The message key (e.g., [ErrorKeys.NETWORK_UNAVAILABLE])
     * @return The localized message string
     */
    fun resolve(key: String): String

    /**
     * Resolves a formatted message for the given key with arguments.
     *
     * Uses string formatting with the pattern from the translation.
     * For example, if the translation is "Failed to load %s items", and args is ["5"],
     * the result will be "Failed to load 5 items".
     *
     * @param key The message key
     * @param args Arguments to format into the message template
     * @return The formatted localized message string
     */
    fun resolve(key: String, args: List<Any>): String
}
