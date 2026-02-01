package io.blackarrows.errors.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Main theme configuration for error UI components.
 *
 * [ErrorTheme] provides a centralized way to customize the appearance of all
 * error components in the arrow-errors library. It follows a tiered customization
 * approach: sensible defaults with escape hatches when needed.
 *
 * ## Tiered Approach
 *
 * 1. **No config** → Material 3 defaults work out of the box
 * 2. **ErrorTheme provided** → Custom global styling via [ErrorThemeProvider]
 * 3. **Component params** → Per-instance overrides take highest priority
 *
 * ## Example
 *
 * ```kotlin
 * // Basic usage - apply theme to your app
 * ErrorThemeProvider(
 *     theme = ErrorTheme(
 *         colors = ErrorColors(
 *             warning = Color(0xFFFF9800)
 *         )
 *     )
 * ) {
 *     // Your app content
 *     MyApp()
 * }
 * ```
 *
 * ```kotlin
 * // Complete customization
 * ErrorThemeProvider(
 *     theme = ErrorTheme(
 *         colors = ErrorColors(
 *             info = Color(0xFF2196F3),
 *             warning = Color(0xFFFF9800),
 *             error = Color(0xFFF44336),
 *             critical = Color(0xFFB71C1C)
 *         ),
 *         spacing = ErrorSpacing(
 *             dialogIconSize = 40.dp,
 *             fullScreenIconSize = 96.dp
 *         ),
 *         typography = ErrorTypography(
 *             titleStyle = TextStyle(fontWeight = FontWeight.Bold)
 *         )
 *     )
 * ) {
 *     MyApp()
 * }
 * ```
 *
 * @property colors Color configuration for error severity levels and surfaces
 * @property spacing Spacing and sizing configuration for error components
 * @property typography Typography configuration for titles and messages
 *
 * @see ErrorColors
 * @see ErrorSpacing
 * @see ErrorTypography
 * @see ErrorThemeProvider
 */
data class ErrorTheme(
    val colors: ErrorColors = ErrorColors(),
    val spacing: ErrorSpacing = ErrorSpacing(),
    val typography: ErrorTypography = ErrorTypography()
)

/**
 * Provides an [ErrorTheme] to the composition tree.
 *
 * Use this composable at the root of your app (or a subtree) to customize
 * the appearance of all error components within that scope.
 *
 * ## Example
 *
 * ```kotlin
 * @Composable
 * fun App() {
 *     MaterialTheme {
 *         ErrorThemeProvider(
 *             theme = ErrorTheme(
 *                 colors = ErrorColors(warning = Color(0xFFFF9800))
 *             )
 *         ) {
 *             // All error components in here will use the custom theme
 *             MainContent()
 *         }
 *     }
 * }
 * ```
 *
 * @param theme The [ErrorTheme] configuration to provide
 * @param content The composable content that will have access to the theme
 */
@Composable
fun ErrorThemeProvider(
    theme: ErrorTheme,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalErrorTheme provides theme) {
        content()
    }
}
