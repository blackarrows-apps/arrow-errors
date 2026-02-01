package io.blackarrows.errors.compose.theme

import androidx.compose.ui.text.TextStyle

/**
 * Typography configuration for error UI components.
 *
 * All text styles default to `null`, which signals the component to use
 * Material 3 typography from [MaterialTheme.typography]. This allows for
 * a tiered customization approach:
 *
 * 1. **No config** → Material 3 typography defaults
 * 2. **Partial config** → Custom styles for specific text, Material 3 for others
 * 3. **Full config** → Complete custom typography
 *
 * ## Default Mapping (when null)
 *
 * - [titleStyle] → Falls back to [MaterialTheme.typography.titleLarge]
 * - [messageStyle] → Falls back to [MaterialTheme.typography.bodyLarge]
 *
 * ## Example
 *
 * ```kotlin
 * // Custom title style only
 * ErrorTypography(
 *     titleStyle = TextStyle(
 *         fontWeight = FontWeight.Bold,
 *         fontSize = 20.sp
 *     )
 * )
 *
 * // Full custom typography
 * ErrorTypography(
 *     titleStyle = TextStyle(
 *         fontFamily = customFontFamily,
 *         fontWeight = FontWeight.Bold,
 *         fontSize = 22.sp
 *     ),
 *     messageStyle = TextStyle(
 *         fontFamily = customFontFamily,
 *         fontSize = 16.sp,
 *         lineHeight = 24.sp
 *     )
 * )
 * ```
 *
 * @property titleStyle Text style for error titles (null = use MaterialTheme)
 * @property messageStyle Text style for error messages (null = use MaterialTheme)
 */
data class ErrorTypography(
    val titleStyle: TextStyle? = null,
    val messageStyle: TextStyle? = null
)
