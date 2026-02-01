package io.blackarrows.errors.compose.theme

import androidx.compose.ui.graphics.Color

/**
 * Color configuration for error UI components.
 *
 * All colors default to [Color.Unspecified], which signals the component to fall back
 * to Material 3 theme colors. This allows for a tiered customization approach:
 *
 * 1. **No config** → Material 3 defaults from [MaterialTheme.colorScheme]
 * 2. **Partial config** → Custom colors for specified severities, Material 3 for others
 * 3. **Full config** → Complete custom color scheme
 *
 * ## Color Mapping (when Unspecified)
 *
 * - [info] → Falls back to [MaterialTheme.colorScheme.primary]
 * - [warning] → Falls back to Amber 400 (#FFA726)
 * - [error] → Falls back to [MaterialTheme.colorScheme.error]
 * - [critical] → Falls back to [MaterialTheme.colorScheme.error]
 * - [surface] → Falls back to [MaterialTheme.colorScheme.surface]
 * - [onSurface] → Falls back to [MaterialTheme.colorScheme.onSurface]
 *
 * ## Example
 *
 * ```kotlin
 * // Only customize warning color, use Material 3 defaults for everything else
 * ErrorColors(
 *     warning = Color(0xFFFF9800)  // Orange 500
 * )
 *
 * // Full custom color scheme
 * ErrorColors(
 *     info = Color(0xFF2196F3),     // Blue 500
 *     warning = Color(0xFFFF9800),  // Orange 500
 *     error = Color(0xFFF44336),    // Red 500
 *     critical = Color(0xFFB71C1C), // Red 900
 *     surface = Color.White,
 *     onSurface = Color.Black
 * )
 * ```
 *
 * @property info Color for informational errors (default: unspecified → primary)
 * @property warning Color for warning errors (default: unspecified → amber)
 * @property error Color for standard errors (default: unspecified → error)
 * @property critical Color for critical errors (default: unspecified → error)
 * @property surface Background color for error components (default: unspecified → surface)
 * @property onSurface Text/icon color on surfaces (default: unspecified → onSurface)
 */
data class ErrorColors(
    val info: Color = Color.Unspecified,
    val warning: Color = Color.Unspecified,
    val error: Color = Color.Unspecified,
    val critical: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val onSurface: Color = Color.Unspecified
)
