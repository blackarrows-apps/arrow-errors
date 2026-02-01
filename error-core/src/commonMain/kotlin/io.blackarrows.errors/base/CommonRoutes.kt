package io.blackarrows.errors.base

/**
 * Common route constants for navigation destinations.
 *
 * These constants provide suggested route strings that map to predefined [ErrorNavigation]
 * destinations. Using these constants instead of string literals provides:
 *
 * - **Consistency**: Same route strings across your app
 * - **Discoverability**: IDE autocomplete for available routes
 * - **Integration**: Works with [suggestedRoute] extension for automatic mapping
 *
 * ## Mapping to ErrorNavigation
 *
 * | Constant | ErrorNavigation |
 * |----------|-----------------|
 * | [LOGIN] | [ErrorNavigation.Login] |
 * | [HOME] | [ErrorNavigation.Home] |
 * | [SETTINGS] | [ErrorNavigation.Settings] |
 * | [SIGNUP] | [ErrorNavigation.Signup] |
 * | [PROFILE] | [ErrorNavigation.Profile] |
 * | [HELP] | [ErrorNavigation.Help] |
 * | [SUPPORT] | [ErrorNavigation.Support] |
 * | [STORE] | [ErrorNavigation.Store] |
 *
 * ## Example Usage
 *
 * ```kotlin
 * // In your NavHost setup
 * NavHost(navController, startDestination = CommonRoutes.HOME) {
 *     composable(CommonRoutes.HOME) { HomeScreen() }
 *     composable(CommonRoutes.LOGIN) { LoginScreen() }
 *     composable(CommonRoutes.SETTINGS) { SettingsScreen() }
 *     composable(CommonRoutes.PROFILE) { ProfileScreen() }
 *     // ...
 * }
 *
 * // Handling error navigation
 * ErrorPresenter(
 *     error = error,
 *     onDismiss = { viewModel.clearError() },
 *     onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *     onNavigate = { navigation ->
 *         navigation?.suggestedRoute()?.let { route ->
 *             navController.navigate(route)
 *         }
 *     }
 * )
 * ```
 *
 * @see ErrorNavigation
 * @see suggestedRoute
 */
object CommonRoutes {
    /** Route for login/sign in screen. */
    const val LOGIN = "login"

    /** Route for home/main screen. */
    const val HOME = "home"

    /** Route for settings screen. */
    const val SETTINGS = "settings"

    /** Route for signup/registration screen. */
    const val SIGNUP = "signup"

    /** Route for user profile screen. */
    const val PROFILE = "profile"

    /** Route for help/documentation screen. */
    const val HELP = "help"

    /** Route for support/contact screen. */
    const val SUPPORT = "support"

    /** Route for store/marketplace screen. */
    const val STORE = "store"
}

/**
 * Returns a suggested route string for this [ErrorNavigation] destination.
 *
 * This extension maps predefined [ErrorNavigation] instances to their corresponding
 * [CommonRoutes] constants. For [ErrorNavigation.Custom], returns the custom route directly.
 * For [ErrorNavigation.Back] or unrecognized navigation types, returns `null`.
 *
 * ## Mapping
 *
 * | ErrorNavigation | Returns |
 * |-----------------|---------|
 * | [ErrorNavigation.Login] | [CommonRoutes.LOGIN] |
 * | [ErrorNavigation.Home] | [CommonRoutes.HOME] |
 * | [ErrorNavigation.Settings] | [CommonRoutes.SETTINGS] |
 * | [ErrorNavigation.Signup] | [CommonRoutes.SIGNUP] |
 * | [ErrorNavigation.Profile] | [CommonRoutes.PROFILE] |
 * | [ErrorNavigation.Help] | [CommonRoutes.HELP] |
 * | [ErrorNavigation.Support] | [CommonRoutes.SUPPORT] |
 * | [ErrorNavigation.Store] | [CommonRoutes.STORE] |
 * | [ErrorNavigation.Custom] | `custom.route` |
 * | [ErrorNavigation.Back] | `null` |
 * | Other | `null` |
 *
 * ## Example Usage
 *
 * ```kotlin
 * fun handleNavigation(navigation: ErrorNavigation?) {
 *     when (navigation) {
 *         ErrorNavigation.Back -> navController.popBackStack()
 *         else -> navigation?.suggestedRoute()?.let { route ->
 *             navController.navigate(route)
 *         }
 *     }
 * }
 * ```
 *
 * ```kotlin
 * // Or use it directly in ErrorPresenter
 * ErrorPresenter(
 *     error = error,
 *     onDismiss = { viewModel.clearError() },
 *     onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *     onNavigate = { nav ->
 *         when (nav) {
 *             ErrorNavigation.Back -> navController.popBackStack()
 *             null -> { /* no navigation */ }
 *             else -> nav.suggestedRoute()?.let { navController.navigate(it) }
 *         }
 *     }
 * )
 * ```
 *
 * @return The suggested route string, or `null` if this navigation doesn't map to a route
 *         (e.g., [ErrorNavigation.Back] which should be handled with `popBackStack()`)
 */
fun ErrorNavigation.suggestedRoute(): String? = when (this) {
    ErrorNavigation.Login -> CommonRoutes.LOGIN
    ErrorNavigation.Home -> CommonRoutes.HOME
    ErrorNavigation.Settings -> CommonRoutes.SETTINGS
    ErrorNavigation.Signup -> CommonRoutes.SIGNUP
    ErrorNavigation.Profile -> CommonRoutes.PROFILE
    ErrorNavigation.Help -> CommonRoutes.HELP
    ErrorNavigation.Support -> CommonRoutes.SUPPORT
    ErrorNavigation.Store -> CommonRoutes.STORE
    is ErrorNavigation.Custom -> route
    else -> null  // Back and any other unknown navigation types
}
