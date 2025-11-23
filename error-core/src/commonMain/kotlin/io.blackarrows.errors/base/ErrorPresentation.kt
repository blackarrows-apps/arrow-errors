package io.blackarrows.errors.base

/**
 * Defines how an error should be presented to the user in the UI.
 *
 * This enum allows errors to specify their preferred presentation style,
 * enabling consistent and appropriate error displays across the application.
 *
 * Example usage:
 * ```kotlin
 * when (exception.presentation) {
 *     ErrorPresentation.Snackbar -> showSnackbar(exception.msg)
 *     ErrorPresentation.Dialog -> showDialog(exception.msg)
 *     ErrorPresentation.FullScreen -> navigateToErrorScreen(exception)
 *     ErrorPresentation.Silent -> logError(exception)
 * }
 * ```
 */
enum class ErrorPresentation {
    /**
     * Display as a temporary snackbar notification at the bottom of the screen.
     *
     * Use for minor errors that don't require user acknowledgment,
     * such as network timeouts or temporary failures.
     */
    Snackbar,

    /**
     * Display as a modal dialog that requires user interaction to dismiss.
     *
     * Use for errors that need user acknowledgment or action,
     * such as validation errors or failed operations.
     */
    Dialog,

    /**
     * Display as a full-screen error page.
     *
     * Use for critical errors that prevent the application from functioning,
     * such as missing required data or fatal configuration errors.
     */
    FullScreen,

    /**
     * Don't show any UI, only log the error.
     *
     * Use for background errors that shouldn't interrupt the user experience
     * but should be tracked for debugging or analytics.
     */
    Silent
}
