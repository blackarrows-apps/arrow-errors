package io.blackarrows.errors.compose.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.compose.theme.ErrorColors
import io.blackarrows.errors.compose.theme.LocalErrorTheme

/**
 * Returns the appropriate Material icon for an error severity.
 *
 * Icon mapping:
 * - [ErrorSeverity.Info]: Info circle (ℹ️)
 * - [ErrorSeverity.Warning]: Warning triangle (⚠️)
 * - [ErrorSeverity.Error]: Error circle with X (❌)
 * - [ErrorSeverity.Critical]: Error circle outlined (🔴)
 *
 * Example:
 * ```kotlin
 * Icon(
 *     imageVector = error.severity.icon(),
 *     contentDescription = null
 * )
 * ```
 */
fun ErrorSeverity.icon(): ImageVector = when (this) {
    ErrorSeverity.Info -> Icons.Filled.Info
    ErrorSeverity.Warning -> Icons.Filled.Warning
    ErrorSeverity.Error -> Icons.Filled.Error
    ErrorSeverity.Critical -> Icons.Filled.ErrorOutline
}

/**
 * Returns the appropriate color for an error severity.
 *
 * This overload uses the [LocalErrorTheme] for custom colors, falling back to
 * Material 3 theme colors when colors are [Color.Unspecified].
 *
 * Color mapping (when no custom theme):
 * - [ErrorSeverity.Info]: Primary color from theme
 * - [ErrorSeverity.Warning]: Amber/Orange (#FFA726)
 * - [ErrorSeverity.Error]: Error color from theme
 * - [ErrorSeverity.Critical]: Error color from theme
 *
 * Example:
 * ```kotlin
 * Icon(
 *     imageVector = error.severity.icon(),
 *     tint = error.severity.color()
 * )
 * ```
 */
@Composable
fun ErrorSeverity.color(): Color {
    val themeColors = LocalErrorTheme.current.colors
    return color(themeColors)
}

/**
 * Returns the appropriate color for an error severity using the provided [ErrorColors].
 *
 * When a color in [colors] is [Color.Unspecified], falls back to Material 3 defaults.
 *
 * @param colors The color configuration to use
 * @return The resolved color for this severity
 */
@Composable
fun ErrorSeverity.color(colors: ErrorColors): Color = when (this) {
    ErrorSeverity.Info -> colors.info.takeOrElse { MaterialTheme.colorScheme.primary }
    ErrorSeverity.Warning -> colors.warning.takeOrElse { Color(0xFFFFA726) }  // Amber 400
    ErrorSeverity.Error -> colors.error.takeOrElse { MaterialTheme.colorScheme.error }
    ErrorSeverity.Critical -> colors.critical.takeOrElse { MaterialTheme.colorScheme.error }
}

/**
 * Returns this color if it's specified, otherwise returns the result of [fallback].
 */
private inline fun Color.takeOrElse(fallback: () -> Color): Color =
    if (this != Color.Unspecified) this else fallback()

/**
 * Returns a human-readable title for an error severity.
 *
 * Title mapping:
 * - [ErrorSeverity.Info]: "Information"
 * - [ErrorSeverity.Warning]: "Warning"
 * - [ErrorSeverity.Error]: "Error"
 * - [ErrorSeverity.Critical]: "Critical Error"
 *
 * Example:
 * ```kotlin
 * Text(text = error.severity.title())
 * ```
 */
fun ErrorSeverity.title(): String = when (this) {
    ErrorSeverity.Info -> "Information"
    ErrorSeverity.Warning -> "Warning"
    ErrorSeverity.Error -> "Error"
    ErrorSeverity.Critical -> "Critical Error"
}
