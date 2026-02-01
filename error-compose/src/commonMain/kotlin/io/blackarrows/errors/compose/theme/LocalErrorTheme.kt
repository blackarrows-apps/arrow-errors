package io.blackarrows.errors.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal for providing [ErrorTheme] through the composition tree.
 *
 * This is a [staticCompositionLocalOf] because theme configuration rarely changes
 * during the lifetime of the composition, making it more efficient than a
 * regular [compositionLocalOf].
 *
 * ## Default Behavior
 *
 * When no [ErrorThemeProvider] is present in the composition tree, this local
 * provides a default [ErrorTheme] with all values set to their defaults (which
 * fall back to Material 3 theme values).
 *
 * ## Usage
 *
 * Components should access the theme via [LocalErrorTheme.current]:
 *
 * ```kotlin
 * @Composable
 * fun MyErrorComponent() {
 *     val theme = LocalErrorTheme.current
 *     val colors = theme.colors
 *     val spacing = theme.spacing
 *     val typography = theme.typography
 *
 *     // Use theme values...
 * }
 * ```
 *
 * @see ErrorTheme
 * @see ErrorThemeProvider
 */
val LocalErrorTheme = staticCompositionLocalOf { ErrorTheme() }
