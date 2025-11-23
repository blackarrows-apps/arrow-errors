package io.blackarrows.errors.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.i18n.MessageResolver

/**
 * Smart error presenter that automatically routes to the correct UI component.
 *
 * This is the main entry point for displaying errors in your application. It automatically
 * selects the appropriate presentation based on the error's [ErrorPresentation] property:
 *
 * - [ErrorPresentation.Dialog] → [ErrorDialog]
 * - [ErrorPresentation.Snackbar] → [ErrorSnackbar]
 * - [ErrorPresentation.FullScreen] → [ErrorFullScreen]
 * - [ErrorPresentation.Silent] → Logs only, no UI (calls onDismiss immediately)
 *
 * This component makes it trivial to handle errors consistently across your app without
 * writing boilerplate UI code.
 *
 * ## Basic Usage
 *
 * ```kotlin
 * @Composable
 * fun MyScreen(viewModel: MyViewModel) {
 *     val error by viewModel.error.collectAsState()
 *
 *     Scaffold { padding ->
 *         MyScreenContent(modifier = Modifier.padding(padding))
 *
 *         // ErrorPresenter handles all error presentations
 *         ErrorPresenter(
 *             error = error,
 *             onDismiss = { viewModel.clearError() },
 *             onActionClick = { actionId ->
 *                 when (actionId) {
 *                     "retry" -> viewModel.retry()
 *                     "dismiss" -> viewModel.clearError()
 *                     else -> { /* handle other actions */ }
 *                 }
 *             },
 *             onNavigate = { navigation ->
 *                 navigation?.let {
 *                     when (it) {
 *                         ErrorNavigation.Login -> navController.navigate("login")
 *                         ErrorNavigation.Back -> navController.popBackStack()
 *                         ErrorNavigation.Home -> navController.navigate("home")
 *                         else -> { /* handle other navigation */ }
 *                     }
 *                 }
 *             }
 *         )
 *     }
 * }
 * ```
 *
 * ## Custom Resolver
 *
 * ```kotlin
 * ErrorPresenter(
 *     error = error,
 *     onDismiss = { viewModel.clearError() },
 *     onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *     onNavigate = { nav -> viewModel.handleNavigation(nav) },
 *     resolver = MyCustomMessageResolver()
 * )
 * ```
 *
 * @param error The actionable exception to display (null if no error)
 * @param onDismiss Callback invoked when the error is dismissed
 * @param onActionClick Callback invoked when an action button is clicked (receives actionId)
 * @param onNavigate Callback invoked to handle navigation directives
 * @param resolver Message resolver for i18n support (defaults to [DefaultMessageResolver])
 * @param modifier Modifier to apply to the error component
 */
@Composable
fun ErrorPresenter(
    error: ActionableException?,
    onDismiss: () -> Unit,
    onActionClick: (actionId: String) -> Unit,
    onNavigate: (ErrorNavigation?) -> Unit,
    resolver: MessageResolver = DefaultMessageResolver,
    modifier: Modifier = Modifier
) {
    error?.let { exception ->
        when (exception.presentation) {
            ErrorPresentation.Dialog -> {
                ErrorDialog(
                    error = exception,
                    onDismiss = onDismiss,
                    onActionClick = onActionClick,
                    onNavigate = onNavigate,
                    resolver = resolver,
                    modifier = modifier
                )
            }

            ErrorPresentation.Snackbar -> {
                ErrorSnackbar(
                    error = exception,
                    onDismiss = onDismiss,
                    onActionClick = onActionClick,
                    resolver = resolver,
                    modifier = modifier
                )
            }

            ErrorPresentation.FullScreen -> {
                ErrorFullScreen(
                    error = exception,
                    onActionClick = onActionClick,
                    onNavigate = onNavigate,
                    resolver = resolver,
                    modifier = modifier
                )
            }

            ErrorPresentation.Silent -> {
                // Silent errors are logged but not shown to the user
                LaunchedEffect(exception) {
                    // You can add logging here if needed
                    println("Silent error: ${exception.id} - ${exception.msg}")
                    onDismiss()
                }
            }
        }
    }
}
