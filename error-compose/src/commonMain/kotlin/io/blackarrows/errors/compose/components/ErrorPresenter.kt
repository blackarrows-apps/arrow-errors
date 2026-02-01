package io.blackarrows.errors.compose.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.i18n.MessageResolver
import io.blackarrows.errors.compose.utils.resolveMessage

/**
 * Request to display a snackbar, used for hoisting snackbar presentation to a parent scaffold.
 *
 * When you have a centralized [Scaffold] with a [SnackbarHost], you can use this data class
 * to delegate snackbar display from [ErrorPresenter] to your parent component.
 *
 * ## Example
 *
 * ```kotlin
 * @Composable
 * fun MyApp() {
 *     val snackbarHostState = remember { SnackbarHostState() }
 *     val scope = rememberCoroutineScope()
 *
 *     Scaffold(
 *         snackbarHost = { SnackbarHost(snackbarHostState) }
 *     ) { padding ->
 *         val error by viewModel.error.collectAsState()
 *
 *         ErrorPresenter(
 *             error = error,
 *             onDismiss = { viewModel.clearError() },
 *             onActionClick = { actionId -> viewModel.handleAction(actionId) },
 *             onNavigate = { nav -> viewModel.handleNavigation(nav) },
 *             onSnackbar = { request ->
 *                 scope.launch {
 *                     val result = snackbarHostState.showSnackbar(
 *                         message = request.message,
 *                         actionLabel = request.actionLabel,
 *                         duration = request.duration
 *                     )
 *                     when (result) {
 *                         SnackbarResult.ActionPerformed -> request.onAction()
 *                         SnackbarResult.Dismissed -> request.onDismiss()
 *                     }
 *                 }
 *             }
 *         )
 *     }
 * }
 * ```
 *
 * @property message The snackbar message to display
 * @property actionLabel Optional label for the action button (null if no action)
 * @property duration How long to show the snackbar
 * @property onAction Callback when the action button is clicked
 * @property onDismiss Callback when the snackbar is dismissed
 */
data class SnackbarRequest(
    val message: String,
    val actionLabel: String?,
    val duration: SnackbarDuration,
    val onAction: () -> Unit,
    val onDismiss: () -> Unit
)

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
 * @param onSnackbar Optional callback to hoist snackbar presentation to a parent scaffold.
 *                   When provided and the error presentation is [ErrorPresentation.Snackbar],
 *                   this callback is invoked instead of rendering an internal [SnackbarHost].
 *                   See [SnackbarRequest] for usage examples.
 * @param modifier Modifier to apply to the error component
 */
@Composable
fun ErrorPresenter(
    error: ActionableException?,
    onDismiss: () -> Unit,
    onActionClick: (actionId: String) -> Unit,
    onNavigate: (ErrorNavigation?) -> Unit,
    resolver: MessageResolver = DefaultMessageResolver,
    onSnackbar: ((SnackbarRequest) -> Unit)? = null,
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
                if (onSnackbar != null) {
                    // Hoist snackbar to parent - resolve messages and create request
                    val message = resolveMessage(exception.msg, resolver)
                    val actionLabel = exception.primaryAction?.let { action ->
                        resolveMessage(action.label, resolver)
                    }
                    val duration = when (exception.severity) {
                        ErrorSeverity.Info -> SnackbarDuration.Short
                        ErrorSeverity.Warning -> SnackbarDuration.Long
                        ErrorSeverity.Error -> SnackbarDuration.Long
                        ErrorSeverity.Critical -> SnackbarDuration.Indefinite
                    }

                    LaunchedEffect(exception) {
                        onSnackbar(
                            SnackbarRequest(
                                message = message,
                                actionLabel = actionLabel,
                                duration = duration,
                                onAction = {
                                    exception.primaryAction?.let { action ->
                                        onActionClick(action.actionId)
                                    }
                                },
                                onDismiss = onDismiss
                            )
                        )
                    }
                } else {
                    // Use internal snackbar host
                    ErrorSnackbar(
                        error = exception,
                        onDismiss = onDismiss,
                        onActionClick = onActionClick,
                        resolver = resolver,
                        modifier = modifier
                    )
                }
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
