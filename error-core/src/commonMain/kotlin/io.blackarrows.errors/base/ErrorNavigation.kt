package io.blackarrows.errors.base

/**
 * Represents navigation actions that can be triggered as a result of an error.
 *
 * This open class provides common navigation destinations that errors might
 * suggest to the user. Applications can extend this class to add custom
 * navigation destinations specific to their needs.
 *
 * Example usage:
 * ```kotlin
 * // Using predefined navigation
 * throw AuthException(navigation = ErrorNavigation.Login)
 *
 * // Extending with custom navigation
 * class CustomNavigation : ErrorNavigation() {
 *     companion object {
 *         val Dashboard = CustomNavigation()
 *     }
 * }
 * ```
 */
open class ErrorNavigation {
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
