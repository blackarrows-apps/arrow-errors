package io.blackarrows.errors.base

/**
 * Common action ID constants for use in `when` blocks and action handlers.
 *
 * These constants correspond to the `actionId` values of predefined [ErrorAction] instances
 * in [ErrorAction.Companion]. Using these constants instead of string literals provides:
 *
 * - **Type safety**: Compile-time checking for typos
 * - **Discoverability**: IDE autocomplete for available action IDs
 * - **Maintainability**: Single source of truth for action ID values
 *
 * ## Mapping to ErrorAction
 *
 * | Constant | ErrorAction |
 * |----------|-------------|
 * | [RETRY] | [ErrorAction.Retry] |
 * | [DISMISS] | [ErrorAction.Dismiss] |
 * | [CANCEL] | [ErrorAction.Cancel] |
 * | [CLOSE] | [ErrorAction.Close] |
 * | [OK] | [ErrorAction.Ok] |
 * | [CONFIRM] | [ErrorAction.Confirm] |
 * | [DELETE] | [ErrorAction.Delete] |
 * | [REMOVE] | [ErrorAction.Remove] |
 *
 * Additional constants ([GO_BACK], [LOGIN], [REFRESH], [CONTACT_SUPPORT]) are provided
 * for common app-specific actions that don't have predefined [ErrorAction] instances.
 *
 * ## Example Usage
 *
 * ```kotlin
 * ErrorPresenter(
 *     error = error,
 *     onDismiss = { viewModel.clearError() },
 *     onActionClick = { actionId ->
 *         when (actionId) {
 *             CommonActionIds.RETRY -> viewModel.retry()
 *             CommonActionIds.DISMISS -> viewModel.clearError()
 *             CommonActionIds.LOGIN -> navController.navigate(CommonRoutes.LOGIN)
 *             CommonActionIds.CONTACT_SUPPORT -> openSupportChat()
 *             else -> { /* handle custom actions */ }
 *         }
 *     },
 *     onNavigate = { nav -> /* handle navigation */ }
 * )
 * ```
 *
 * ## Creating Custom Actions with These IDs
 *
 * ```kotlin
 * // Custom action using a common ID
 * val loginAction = ErrorAction.Custom(
 *     actionId = CommonActionIds.LOGIN,
 *     label = UiMessage.Plain("Sign In")
 * )
 *
 * throw AuthException(
 *     primaryAction = loginAction,
 *     navigation = ErrorNavigation.Login
 * )
 * ```
 */
object CommonActionIds {
    /** Retry the failed operation. Maps to [ErrorAction.Retry]. */
    const val RETRY = "retry"

    /** Dismiss the error without action. Maps to [ErrorAction.Dismiss]. */
    const val DISMISS = "dismiss"

    /** Cancel the current operation. Maps to [ErrorAction.Cancel]. */
    const val CANCEL = "cancel"

    /** Close the error dialog/screen. Maps to [ErrorAction.Close]. */
    const val CLOSE = "close"

    /** Acknowledge the error (OK button). Maps to [ErrorAction.Ok]. */
    const val OK = "ok"

    /** Confirm a destructive or important action. Maps to [ErrorAction.Confirm]. */
    const val CONFIRM = "confirm"

    /** Delete something (destructive). Maps to [ErrorAction.Delete]. */
    const val DELETE = "delete"

    /** Remove something (destructive). Maps to [ErrorAction.Remove]. */
    const val REMOVE = "remove"

    /** Navigate back to the previous screen. */
    const val GO_BACK = "go_back"

    /** Navigate to login/sign in. */
    const val LOGIN = "login"

    /** Refresh/reload the current content. */
    const val REFRESH = "refresh"

    /** Open support/help contact. */
    const val CONTACT_SUPPORT = "contact_support"
}
