package io.blackarrows.errors.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Main activity for the Arrow Errors Playground sample app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                val viewModel: ErrorPlaygroundViewModel = viewModel()
                ErrorPlaygroundScreen(viewModel)
            }
        }
    }
}
