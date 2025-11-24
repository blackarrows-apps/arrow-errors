package io.blackarrows.errors.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.i18n.MessageResolver
import io.blackarrows.errors.compose.utils.resolveMessage

/**
 * A Material 3 snackbar for displaying brief error notifications.
 *
 * This component provides a non-intrusive way to display errors:
 * - Automatically shows at the bottom of the screen
 * - Duration based on error severity (Info=Short, Warning/Error=Long, Critical=Indefinite)
 * - Supports a single action button (primary action)
 * - Resolves i18n messages using the provided [MessageResolver]
 *
 * ## Basic Usage
 *
 * ```kotlin
 * val snackbarHostState = remember { SnackbarHostState() }
 *
 * Scaffold(
 *     snackbarHost = {
 *         val error by viewModel.error.collectAsState()
 *         error?.let {
 *             ErrorSnackbar(
 *                 error = it,
 *                 onDismiss = { viewModel.clearError() },
 *                 onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *                 snackbarHostState = snackbarHostState
 *             )
 *         }
 *     }
 * ) {
 *     // Your content
 * }
 * ```
 *
 * ## Standalone Usage
 *
 * ```kotlin
 * Box {
 *     MyScreenContent()
 *
 *     val error by viewModel.error.collectAsState()
 *     error?.let {
 *         ErrorSnackbar(
 *             error = it,
 *             onDismiss = { viewModel.clearError() },
 *             onActionClick = { actionId -> viewModel.handleAction(actionId) }
 *         )
 *     }
 * }
 * ```
 *
 * @param error The actionable exception to display
 * @param onDismiss Callback invoked when the snackbar is dismissed
 * @param onActionClick Callback invoked when the action button is clicked (receives actionId)
 * @param resolver Message resolver for i18n support (defaults to [DefaultMessageResolver])
 * @param snackbarHostState The state for the snackbar host (defaults to a remembered instance)
 * @param modifier Modifier to apply to the snackbar host
 */
@Composable
fun ErrorSnackbar(
    error: ActionableException,
    onDismiss: () -> Unit,
    onActionClick: (actionId: String) -> Unit,
    resolver: MessageResolver = DefaultMessageResolver,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier
) {
    // Resolve messages outside of LaunchedEffect
    val message = resolveMessage(error.msg, resolver)
    val actionLabel = error.primaryAction?.let { action ->
        resolveMessage(action.label, resolver)
    }

    LaunchedEffect(error) {
        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            withDismissAction = true,
            duration = when (error.severity) {
                ErrorSeverity.Info -> SnackbarDuration.Short
                ErrorSeverity.Warning -> SnackbarDuration.Long
                ErrorSeverity.Error -> SnackbarDuration.Long
                ErrorSeverity.Critical -> SnackbarDuration.Indefinite
            }
        )

        when (result) {
            SnackbarResult.ActionPerformed -> {
                error.primaryAction?.let { action ->
                    onActionClick(action.actionId)
                }
            }
            SnackbarResult.Dismissed -> {
                onDismiss()
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
