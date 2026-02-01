package io.blackarrows.errors.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import io.blackarrows.errors.compose.theme.ErrorColors
import io.blackarrows.errors.compose.theme.ErrorTheme
import io.blackarrows.errors.compose.theme.ErrorThemeProvider

/**
 * Main activity for the Arrow Errors Playground sample app.
 *
 * Demonstrates v1.1.0 features including:
 * - Custom ErrorTheme with custom warning color
 * - Snackbar hoisting to centralized Scaffold
 * - CommonActionIds for type-safe action handling
 * - toActionableException() for error conversion
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                // v1.1.0: Wrap with ErrorThemeProvider for custom theming
                ErrorThemeProvider(
                    theme = ErrorTheme(
                        colors = ErrorColors(
                            // Custom warning color (Orange 600)
                            warning = Color(0xFFFB8C00),
                            // Custom info color (Blue 600)
                            info = Color(0xFF1E88E5)
                        )
                    )
                ) {
                    val viewModel: ErrorPlaygroundViewModel = viewModel()
                    ErrorPlaygroundScreen(viewModel)
                }
            }
        }
    }
}
