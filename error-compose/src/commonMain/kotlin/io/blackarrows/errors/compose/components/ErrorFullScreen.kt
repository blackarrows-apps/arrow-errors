package io.blackarrows.errors.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.i18n.MessageResolver
import io.blackarrows.errors.compose.utils.color
import io.blackarrows.errors.compose.utils.icon
import io.blackarrows.errors.compose.utils.resolveMessage

/**
 * A full-screen error state component for critical errors.
 *
 * This component is used for severe errors that should block the entire UI:
 * - Takes up the full screen
 * - Centers content vertically and horizontally
 * - Large, prominent error icon
 * - Clear error message
 * - Primary and secondary action buttons
 * - Typically used for [io.blackarrows.errors.base.ErrorPresentation.FullScreen] errors
 *
 * ## Basic Usage
 *
 * ```kotlin
 * val error by viewModel.error.collectAsState()
 *
 * if (error?.presentation == ErrorPresentation.FullScreen) {
 *     ErrorFullScreen(
 *         error = error!!,
 *         onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *         onNavigate = { nav -> viewModel.handleNavigation(nav) }
 *     )
 * } else {
 *     // Regular content
 *     MyScreenContent()
 * }
 * ```
 *
 * ## Customization
 *
 * ```kotlin
 * ErrorFullScreen(
 *     error = criticalError,
 *     onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *     onNavigate = { nav -> viewModel.handleNavigation(nav) },
 *     backgroundColor = Color.Black,
 *     messageStyle = MaterialTheme.typography.headlineSmall
 * )
 * ```
 *
 * @param error The actionable exception to display
 * @param onActionClick Callback invoked when an action button is clicked (receives actionId)
 * @param onNavigate Callback invoked to handle navigation directives
 * @param resolver Message resolver for i18n support (defaults to [DefaultMessageResolver])
 * @param modifier Modifier to apply to the root container
 * @param backgroundColor Background color of the screen (defaults to theme's surface)
 * @param messageStyle Text style for the message (defaults to theme's headlineSmall)
 * @param icon Custom icon composable (defaults to severity-based icon)
 * @param content Custom content composable (defaults to error message)
 * @param actions Custom actions composable (defaults to primary/secondary action buttons)
 */
@Composable
fun ErrorFullScreen(
    error: ActionableException,
    onActionClick: (actionId: String) -> Unit,
    onNavigate: (ErrorNavigation?) -> Unit,
    resolver: MessageResolver = DefaultMessageResolver,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    messageStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    icon: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            // Large error icon
            icon?.invoke() ?: Icon(
                imageVector = error.severity.icon(),
                contentDescription = error.severity.name,
                tint = error.severity.color(),
                modifier = Modifier.size(72.dp)
            )

            // Error message
            content?.invoke() ?: Text(
                text = resolveMessage(error.msg, resolver),
                style = messageStyle,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            actions?.invoke() ?: Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Primary action
                error.primaryAction?.let { action ->
                    Button(
                        onClick = {
                            onActionClick(action.actionId)
                            onNavigate(error.navigation)
                        },
                        colors = if (action.isDestructive) {
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        } else {
                            ButtonDefaults.buttonColors()
                        },
                        modifier = Modifier.widthIn(min = 200.dp)
                    ) {
                        Text(resolveMessage(action.label, resolver))
                    }
                }

                // Secondary action
                error.secondaryAction?.let { action ->
                    TextButton(
                        onClick = {
                            onActionClick(action.actionId)
                        }
                    ) {
                        Text(resolveMessage(action.label, resolver))
                    }
                }
            }
        }
    }
}
