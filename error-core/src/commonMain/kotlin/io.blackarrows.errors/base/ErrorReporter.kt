package io.blackarrows.errors.base

/**
 * Interface for reporting errors to logging and analytics systems.
 *
 * Implementations can integrate with various backends such as:
 * - Firebase Crashlytics
 * - Custom analytics platforms
 * - Logging frameworks
 * - Error tracking services
 *
 * Example implementation:
 * ```kotlin
 * class FirebaseErrorReporter : ErrorReporter {
 *     override fun report(error: ActionableException) {
 *         Firebase.crashlytics.recordException(error)
 *         Analytics.logEvent("error_shown", mapOf(
 *             "error_id" to error.id,
 *             "severity" to error.severity.name
 *         ))
 *     }
 * }
 * ```
 *
 * Register reporters at application startup:
 * ```kotlin
 * ErrorReporting.addReporter(FirebaseErrorReporter())
 * ```
 */
interface ErrorReporter {
    /**
     * Reports an error to the logging/analytics system.
     *
     * @param error The error to report
     */
    fun report(error: ActionableException)
}
