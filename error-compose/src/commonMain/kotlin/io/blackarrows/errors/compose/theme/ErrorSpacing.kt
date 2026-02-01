package io.blackarrows.errors.compose.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing and sizing configuration for error UI components.
 *
 * Provides sensible defaults that work well with Material 3 design guidelines.
 * Customize individual values to match your app's design system.
 *
 * ## Default Values
 *
 * - [dialogIconSize] = 32.dp - Icon size in error dialogs
 * - [fullScreenIconSize] = 72.dp - Large icon for full-screen errors
 * - [contentPadding] = 16.dp - Standard padding around content
 * - [itemSpacing] = 8.dp - Space between items in lists/rows
 *
 * ## Example
 *
 * ```kotlin
 * // Increase icon sizes for better visibility
 * ErrorSpacing(
 *     dialogIconSize = 40.dp,
 *     fullScreenIconSize = 96.dp
 * )
 *
 * // More compact layout
 * ErrorSpacing(
 *     contentPadding = 12.dp,
 *     itemSpacing = 4.dp
 * )
 * ```
 *
 * @property dialogIconSize Size of the icon in error dialogs
 * @property fullScreenIconSize Size of the icon in full-screen error states
 * @property contentPadding Padding around content in error components
 * @property itemSpacing Space between items (buttons, text elements, etc.)
 */
data class ErrorSpacing(
    val dialogIconSize: Dp = 32.dp,
    val fullScreenIconSize: Dp = 72.dp,
    val contentPadding: Dp = 16.dp,
    val itemSpacing: Dp = 8.dp
)
