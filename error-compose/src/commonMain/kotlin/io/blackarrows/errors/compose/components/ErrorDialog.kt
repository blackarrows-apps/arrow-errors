package io.blackarrows.errors.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.i18n.MessageResolver
import io.blackarrows.errors.compose.utils.color
import io.blackarrows.errors.compose.utils.icon
import io.blackarrows.errors.compose.utils.resolveMessage
import io.blackarrows.errors.compose.utils.title

/**
 * A Material 3 alert dialog for displaying actionable errors.
 *
 * This component provides a generic, reusable error dialog that:
 * - Automatically displays the appropriate icon and color based on error severity
 * - Resolves i18n messages using the provided [MessageResolver]
 * - Handles primary and secondary actions
 * - Supports navigation directives
 * - Fully customizable via composable slots
 *
 * ## Basic Usage
 *
 * ```kotlin
 * ErrorDialog(
 *     error = networkException(error = IOException()),
 *     onDismiss = { viewModel.clearError() },
 *     onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *     onNavigate = { nav -> viewModel.handleNavigation(nav) }
 * )
 * ```
 *
 * ## Customization with Slots
 *
 * ```kotlin
 * ErrorDialog(
 *     error = paymentError,
 *     onDismiss = { viewModel.clearError() },
 *     onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *     onNavigate = { nav -> viewModel.handleNavigation(nav) },
 *     content = {
 *         // Custom content for payment errors
 *         Column {
 *             Text("Payment failed for order #${paymentError.orderId}")
 *             Text(resolveMessage(paymentError.msg))
 *         }
 *     }
 * )
 * ```
 *
 * @param error The actionable exception to display
 * @param onDismiss Callback invoked when the dialog is dismissed
 * @param onActionClick Callback invoked when an action button is clicked (receives actionId)
 * @param onNavigate Callback invoked to handle navigation directives
 * @param resolver Message resolver for i18n support (defaults to [DefaultMessageResolver])
 * @param modifier Modifier to apply to the dialog
 * @param icon Custom icon composable (defaults to severity-based icon)
 * @param title Custom title composable (defaults to severity-based title)
 * @param content Custom content composable (defaults to error message)
 * @param actions Custom actions composable (defaults to primary/secondary action buttons)
 * @param titleStyle Text style for the title (defaults to theme's titleLarge)
 * @param messageStyle Text style for the message (defaults to theme's bodyLarge)
 * @param containerColor Background color of the dialog (defaults to theme's surface)
 */
@Composable
fun ErrorDialog(
    error: ActionableException,
    onDismiss: () -> Unit,
    onActionClick: (actionId: String) -> Unit,
    onNavigate: (ErrorNavigation?) -> Unit,
    resolver: MessageResolver = DefaultMessageResolver,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    messageStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = icon ?: {
            Icon(
                imageVector = error.severity.icon(),
                contentDescription = error.severity.name,
                tint = error.severity.color(),
                modifier = Modifier.size(32.dp)
            )
        },
        title = title ?: {
            Text(
                text = error.severity.title(),
                style = titleStyle
            )
        },
        text = content ?: {
            Text(
                text = resolveMessage(error.msg, resolver),
                style = messageStyle
            )
        },
        confirmButton = actions ?: {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                error.secondaryAction?.let { action ->
                    TextButton(
                        onClick = {
                            onActionClick(action.actionId)
                            onDismiss()
                        }
                    ) {
                        Text(resolveMessage(action.label, resolver))
                    }
                }

                error.primaryAction?.let { action ->
                    Button(
                        onClick = {
                            onActionClick(action.actionId)
                            onNavigate(error.navigation)
                            onDismiss()
                        },
                        colors = if (action.isDestructive) {
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        } else {
                            ButtonDefaults.buttonColors()
                        }
                    ) {
                        Text(resolveMessage(action.label, resolver))
                    }
                }
            }
        },
        containerColor = containerColor,
        modifier = modifier
    )
}
