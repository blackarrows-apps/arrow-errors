package io.blackarrows.errors.base

/**
 * Represents navigation actions that can be triggered as a result of an error.
 *
 * This open class provides common navigation destinations that errors might
 * suggest to the user. Applications can use the [Custom] subclass to create
 * app-specific navigation routes.
 *
 * Example usage:
 * ```kotlin
 * // Using predefined navigation
 * throw AuthException(navigation = ErrorNavigation.Login)
 *
 * // Using custom navigation with routes
 * throw VideoException(
 *     navigation = ErrorNavigation.Custom("video/player?id=123")
 * )
 *
 * // Or extending with custom navigation classes
 * class CustomNavigation : ErrorNavigation() {
 *     companion object {
 *         val Dashboard = CustomNavigation()
 *     }
 * }
 * ```
 */
open class ErrorNavigation {
    /**
     * Custom navigation route for app-specific destinations.
     *
     * Use this to navigate to routes specific to your application that aren't
     * covered by the predefined navigation destinations.
     *
     * Example:
     * ```kotlin
     * // Navigate to a specific screen with parameters
     * ErrorNavigation.Custom("dashboard/users?filter=active")
     *
     * // Navigate to a deep link
     * ErrorNavigation.Custom("myapp://auth/reset-password")
     * ```
     *
     * @property route The destination route string (can be a deep link, navigation route, etc.)
     */
    data class Custom(val route: String) : ErrorNavigation()

    companion object {
        /**
         * Navigate back to the previous screen.
         */
        val Back = ErrorNavigation()

        /**
         * Navigate to the home or main screen.
         */
        val Home = ErrorNavigation()

        /**
         * Navigate to the login screen.
         *
         * Typically used for authentication errors or session expiration.
         */
        val Login = ErrorNavigation()

        /**
         * Navigate to the signup screen.
         *
         * Used when users need to create an account.
         */
        val Signup = ErrorNavigation()

        /**
         * Navigate to the settings screen.
         *
         * Used when configuration or preference changes are needed.
         */
        val Settings = ErrorNavigation()

        /**
         * Navigate to the user profile screen.
         */
        val Profile = ErrorNavigation()

        /**
         * Navigate to the help or documentation screen.
         */
        val Help = ErrorNavigation()

        /**
         * Navigate to the support or contact screen.
         *
         * Used when users need assistance with an error.
         */
        val Support = ErrorNavigation()

        /**
         * Navigate to the store or marketplace screen.
         */
        val Store = ErrorNavigation()
    }
}
