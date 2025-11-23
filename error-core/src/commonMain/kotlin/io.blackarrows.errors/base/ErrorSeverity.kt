package io.blackarrows.errors.base

/**
 * Defines the severity level of an error.
 *
 * This enum helps categorize errors based on their impact, allowing applications
 * to handle them appropriately (e.g., logging, UI presentation, alerting).
 *
 * Example usage:
 * ```kotlin
 * when (exception.severity) {
 *     ErrorSeverity.Info -> log.info(exception.message)
 *     ErrorSeverity.Warning -> log.warn(exception.message)
 *     ErrorSeverity.Error -> log.error(exception.message)
 *     ErrorSeverity.Critical -> {
 *         log.error(exception.message)
 *         notifyAdmins()
 *     }
 * }
 * ```
 */
enum class ErrorSeverity {
    /**
     * Informational message that doesn't indicate a problem.
     *
     * Use for non-critical notifications or status updates.
     */
    Info,

    /**
     * Warning that something might be wrong but operation can continue.
     *
     * Use for recoverable issues that don't prevent functionality but
     * should be brought to the user's attention.
     */
    Warning,

    /**
     * Error that prevents an operation from completing successfully.
     *
     * Use for failures that stop the current operation but don't
     * affect the overall application state.
     */
    Error,

    /**
     * Critical error that may affect the entire application.
     *
     * Use for severe failures that require immediate attention,
     * such as authentication failures, data corruption, or system errors.
     */
    Critical
}
