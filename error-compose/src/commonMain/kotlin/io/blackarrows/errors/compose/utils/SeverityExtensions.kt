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
 * Color mapping:
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
fun ErrorSeverity.color(): Color = when (this) {
    ErrorSeverity.Info -> MaterialTheme.colorScheme.primary
    ErrorSeverity.Warning -> Color(0xFFFFA726)  // Amber 400
    ErrorSeverity.Error -> MaterialTheme.colorScheme.error
    ErrorSeverity.Critical -> MaterialTheme.colorScheme.error
}

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
