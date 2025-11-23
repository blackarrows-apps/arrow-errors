package io.blackarrows.errors.base

/**
 * Global registry for error reporters.
 *
 * Allows consumers to register multiple error reporters that will be notified
 * whenever an error occurs. This enables integration with logging, analytics,
 * and crash reporting systems without coupling the error-core module to
 * specific implementations.
 *
 * Example usage:
 * ```kotlin
 * // Register a reporter at app startup
 * ErrorReporting.addReporter(FirebaseErrorReporter())
 * ErrorReporting.addReporter(CustomAnalyticsReporter())
 *
 * // Errors will automatically be reported to all registered reporters
 * throw NetworkException(...)
 *
 * // Remove a reporter if needed
 * ErrorReporting.removeReporter(reporter)
 * ```
 */
object ErrorReporting {
    private val reporters = mutableListOf<ErrorReporter>()

    /**
     * Adds an error reporter to the registry.
     *
     * @param reporter The reporter to add
     */
    fun addReporter(reporter: ErrorReporter) {
        reporters.add(reporter)
    }

    /**
     * Removes an error reporter from the registry.
     *
     * @param reporter The reporter to remove
     */
    fun removeReporter(reporter: ErrorReporter) {
        reporters.remove(reporter)
    }

    /**
     * Removes all registered error reporters.
     */
    fun clearReporters() {
        reporters.clear()
    }

    /**
     * Reports an error to all registered reporters.
     *
     * This is called internally by the error handling system.
     * Consumers typically don't need to call this directly.
     *
     * @param error The error to report
     */
    fun reportError(error: ActionableException) {
        reporters.forEach { reporter ->
            try {
                reporter.report(error)
            } catch (e: Exception) {
                // Silently ignore reporter failures to prevent cascading errors
                // Consumers can implement their own error handling within reporters
            }
        }
    }
}
