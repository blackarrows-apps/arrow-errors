package io.blackarrows.errors.catalog.i18n

import io.blackarrows.errors.catalog.i18n.translations.englishTranslations
import io.blackarrows.errors.catalog.i18n.translations.frenchTranslations
import io.blackarrows.errors.catalog.i18n.translations.spanishTranslations

/**
 * Default implementation of [MessageResolver] that uses built-in translation maps.
 *
 * Supports the following languages out of the box:
 * - English (en) - default fallback
 * - Spanish (es)
 * - French (fr)
 *
 * ## Thread Safety
 * This implementation is NOT thread-safe. The locale should be set once during
 * app initialization before any concurrent access.
 *
 * ## Usage
 *
 * ### Basic Usage (Set Locale Once)
 * ```kotlin
 * // During app initialization
 * DefaultMessageResolver.setLocale("es")
 *
 * // Use throughout the app
 * val message = DefaultMessageResolver.resolve(ErrorKeys.NETWORK_UNAVAILABLE)
 * // Returns: "La red no está disponible. Por favor, verifica tu conexión."
 * ```
 *
 * ### With Exception Factory Functions
 * ```kotlin
 * // Set locale once
 * DefaultMessageResolver.setLocale("fr")
 *
 * // Factory functions automatically use DefaultMessageResolver
 * throw networkException(
 *     error = IOException("Connection failed")
 * )
 * // The error message will be in French
 * ```
 *
 * ### Fallback Behavior
 * If a translation is not found for the current locale, it falls back to English.
 * If the key doesn't exist in English either, returns a generic error message.
 *
 * ```kotlin
 * DefaultMessageResolver.setLocale("es")
 * val message = DefaultMessageResolver.resolve("unknown.key")
 * // Returns: "Error occurred (missing translation: unknown.key)"
 * ```
 *
 * @see MessageResolver
 * @see ErrorKeys
 */
object DefaultMessageResolver : MessageResolver {

    private val supportedLocales = setOf("en", "es", "fr")

    private var currentLocale: String = "en"

    private var currentTranslations: Map<String, String> = englishTranslations

    /**
     * Sets the current locale for message resolution.
     *
     * Supported locales:
     * - "en" - English (default)
     * - "es" - Spanish
     * - "fr" - French
     *
     * If an unsupported locale is provided, it will fall back to English.
     *
     * **Thread Safety:** This method is not thread-safe. It should be called once
     * during application initialization before any concurrent access.
     *
     * @param locale The locale code (e.g., "en", "es", "fr")
     */
    fun setLocale(locale: String) {
        val normalizedLocale = locale.lowercase().take(2)
        currentLocale = normalizedLocale
        currentTranslations = when (normalizedLocale) {
            "es" -> spanishTranslations
            "fr" -> frenchTranslations
            else -> englishTranslations
        }
    }

    /**
     * Gets the current locale.
     *
     * @return The current locale code (e.g., "en", "es", "fr")
     */
    fun getLocale(): String = currentLocale

    /**
     * Gets the list of supported locales.
     *
     * @return Set of supported locale codes
     */
    fun getSupportedLocales(): Set<String> = supportedLocales

    /**
     * Resolves a message for the given key using the current locale.
     *
     * Falls back to English if the key is not found in the current locale.
     * If the key doesn't exist in English either, returns a generic error message.
     *
     * @param key The message key (e.g., [ErrorKeys.NETWORK_UNAVAILABLE])
     * @return The localized message string
     */
    override fun resolve(key: String): String {
        return currentTranslations[key]
            ?: englishTranslations[key]
            ?: "Error occurred (missing translation: $key)"
    }

    /**
     * Resolves a formatted message for the given key with arguments.
     *
     * Uses simple placeholder replacement: {0}, {1}, {2}, etc.
     * Falls back to English if the key is not found in the current locale.
     *
     * Example:
     * ```kotlin
     * // Assuming translation: "Failed to load {0} items"
     * val message = DefaultMessageResolver.resolve("items.load.failed", listOf(5))
     * // Returns: "Failed to load 5 items"
     * ```
     *
     * @param key The message key
     * @param args Arguments to format into the message template
     * @return The formatted localized message string
     */
    override fun resolve(key: String, args: List<Any>): String {
        val template = currentTranslations[key]
            ?: englishTranslations[key]
            ?: return "Error occurred (missing translation: $key)"

        return try {
            var result = template
            args.forEachIndexed { index, arg ->
                result = result.replace("{$index}", arg.toString())
            }
            result
        } catch (e: Exception) {
            // If formatting fails, return the template as-is
            template
        }
    }
}
