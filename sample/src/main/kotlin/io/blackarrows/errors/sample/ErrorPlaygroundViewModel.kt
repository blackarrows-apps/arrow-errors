package io.blackarrows.errors.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.CommonActionIds
import io.blackarrows.errors.base.CommonRoutes
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage
import io.blackarrows.errors.base.suggestedRoute
import io.blackarrows.errors.catalog.factories.*
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.extensions.toActionableException
import io.blackarrows.errors.network.NetworkException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

/**
 * ViewModel for the Error Playground sample app.
 *
 * Provides methods to trigger various error scenarios to demonstrate
 * the arrow-errors library capabilities.
 */
class ErrorPlaygroundViewModel : ViewModel() {
    private val _error = MutableStateFlow<ActionableException?>(null)
    val error: StateFlow<ActionableException?> = _error.asStateFlow()

    private val _locale = MutableStateFlow("en")
    val locale: StateFlow<String> = _locale.asStateFlow()

    private val _lastAction = MutableStateFlow<String?>(null)
    val lastAction: StateFlow<String?> = _lastAction.asStateFlow()

    private val _lastNavigation = MutableStateFlow<String?>(null)
    val lastNavigation: StateFlow<String?> = _lastNavigation.asStateFlow()

    // Custom action/navigation inputs
    private val _customActionId = MutableStateFlow("my_custom_action")
    val customActionId: StateFlow<String> = _customActionId.asStateFlow()

    private val _customActionLabel = MutableStateFlow("Custom Action")
    val customActionLabel: StateFlow<String> = _customActionLabel.asStateFlow()

    private val _customNavigationRoute = MutableStateFlow("app://custom/route")
    val customNavigationRoute: StateFlow<String> = _customNavigationRoute.asStateFlow()

    init {
        // Set default locale
        DefaultMessageResolver.setLocale("en")
    }

    // ========== Custom Input Management ==========

    fun updateCustomActionId(value: String) {
        _customActionId.value = value
    }

    fun updateCustomActionLabel(value: String) {
        _customActionLabel.value = value
    }

    fun updateCustomNavigationRoute(value: String) {
        _customNavigationRoute.value = value
    }

    // ========== Locale Management ==========

    fun setLocale(locale: String) {
        _locale.value = locale
        DefaultMessageResolver.setLocale(locale)
    }

    // ========== Error Management ==========

    fun clearError() {
        _error.value = null
    }

    /**
     * Handles action clicks using CommonActionIds for type-safe matching.
     * v1.1.0: Demonstrates using CommonActionIds constants in when blocks.
     */
    fun handleAction(actionId: String) {
        _lastAction.value = when (actionId) {
            CommonActionIds.RETRY -> "Action: Retry operation"
            CommonActionIds.DISMISS -> "Action: Dismissed error"
            CommonActionIds.CANCEL -> "Action: Cancelled operation"
            CommonActionIds.CLOSE -> "Action: Closed dialog"
            CommonActionIds.OK -> "Action: Acknowledged"
            CommonActionIds.CONFIRM -> "Action: Confirmed"
            CommonActionIds.DELETE -> "Action: Deleted (destructive)"
            CommonActionIds.REMOVE -> "Action: Removed (destructive)"
            CommonActionIds.LOGIN -> "Action: Navigate to login"
            CommonActionIds.CONTACT_SUPPORT -> "Action: Contact support"
            CommonActionIds.REFRESH -> "Action: Refresh content"
            CommonActionIds.GO_BACK -> "Action: Go back"
            else -> "Action clicked: $actionId (custom)"
        }
    }

    /**
     * Handles navigation using suggestedRoute() for automatic route mapping.
     * v1.1.0: Demonstrates using suggestedRoute() extension.
     */
    fun handleNavigation(navigation: ErrorNavigation?) {
        navigation?.let {
            val route = it.suggestedRoute()
            _lastNavigation.value = when {
                it == ErrorNavigation.Back -> "Navigation: Back (popBackStack)"
                route != null -> "Navigation: Route -> $route"
                else -> "Navigation: ${it::class.simpleName}"
            }
        }
    }

    // ========== Network Errors ==========

    fun showNetworkErrorSnackbar() {
        _error.value = networkException(
            error = Exception("Connection timeout")
        )
    }

    fun showNetworkErrorDialog() {
        _error.value = networkException(
            presentation = ErrorPresentation.Dialog,
            error = Exception("Unable to reach server")
        )
    }

    fun showNetworkErrorFullScreen() {
        _error.value = networkException(
            presentation = ErrorPresentation.FullScreen,
            severity = ErrorSeverity.Critical,
            error = Exception("Network completely unavailable")
        )
    }

    // ========== Auth Errors ==========

    fun showAuthExpiredDialog() {
        _error.value = tokenExpiredException(
            error = Exception("Token expired")
        )
    }

    fun showAuthUnauthorizedDialog() {
        _error.value = unauthorizedException(
            error = Exception("Unauthorized access")
        )
    }

    fun showAuthForbiddenDialog() {
        _error.value = forbiddenException(
            error = Exception("Forbidden resource")
        )
    }

    // ========== Storage Errors ==========

    fun showStorageErrorDialog() {
        _error.value = storageException(
            presentation = ErrorPresentation.Dialog,
            error = Exception("Storage operation failed")
        )
    }

    fun showStorageFullError() {
        _error.value = insufficientSpaceException(
            presentation = ErrorPresentation.Dialog,
            error = Exception("Disk full")
        )
    }

    // ========== Session Errors ==========

    fun showSessionFailedError() {
        _error.value = sessionFailedException(
            presentation = ErrorPresentation.Dialog,
            error = Exception("Session validation failed")
        )
    }

    // ========== Custom Errors ==========

    fun showCustomErrorWithNavigation() {
        _error.value = ActionableException(
            id = "custom_error",
            msg = UiMessage.Plain("This is a custom error with navigation to Settings"),
            severity = ErrorSeverity.Warning,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction(
                actionId = "open_settings",
                label = UiMessage.Plain("Open Settings")
            ),
            secondaryAction = ErrorAction.Dismiss,
            navigation = ErrorNavigation.Settings
        )
    }

    fun showCustomErrorWithMultipleActions() {
        _error.value = ActionableException(
            id = "multiple_actions",
            msg = UiMessage.Plain("Choose an action to proceed"),
            severity = ErrorSeverity.Info,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction.Confirm,
            secondaryAction = ErrorAction.Cancel
        )
    }

    fun showDestructiveActionError() {
        _error.value = ActionableException(
            id = "destructive_action",
            msg = UiMessage.Plain("This action will delete all data. Are you sure?"),
            severity = ErrorSeverity.Critical,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction.Delete,
            secondaryAction = ErrorAction.Cancel
        )
    }

    // ========== Severity Variations ==========

    fun showInfoMessage() {
        _error.value = ActionableException(
            id = "info_message",
            msg = UiMessage.Plain("This is an informational message"),
            severity = ErrorSeverity.Info,
            presentation = ErrorPresentation.Snackbar,
            primaryAction = ErrorAction.Ok
        )
    }

    fun showWarningMessage() {
        _error.value = ActionableException(
            id = "warning_message",
            msg = UiMessage.Plain("This is a warning message"),
            severity = ErrorSeverity.Warning,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction.Ok
        )
    }

    fun showErrorMessage() {
        _error.value = ActionableException(
            id = "error_message",
            msg = UiMessage.Plain("This is a standard error"),
            severity = ErrorSeverity.Error,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction.Retry,
            secondaryAction = ErrorAction.Dismiss
        )
    }

    fun showCriticalError() {
        _error.value = ActionableException(
            id = "critical_error",
            msg = UiMessage.Plain("This is a critical error that requires immediate attention"),
            severity = ErrorSeverity.Critical,
            presentation = ErrorPresentation.FullScreen,
            primaryAction = ErrorAction(
                actionId = "contact_support",
                label = UiMessage.Plain("Contact Support")
            )
        )
    }

    // ========== Silent Error ==========

    fun showSilentError() {
        _error.value = ActionableException(
            id = "silent_error",
            msg = UiMessage.Plain("This error is logged but not shown to the user"),
            severity = ErrorSeverity.Info,
            presentation = ErrorPresentation.Silent
        )
        _lastAction.value = "Silent error triggered (check logs)"
    }

    // ========== Custom Actions & Navigation ==========

    /**
     * Demonstrates a custom action that's specific to the app.
     * This shows how to use ErrorAction.Custom for app-specific actions
     * that aren't covered by the predefined actions.
     */
    fun showCustomActionExample() {
        _error.value = ActionableException(
            id = "video_playback_error",
            msg = UiMessage.Plain("Unable to play this video. You can skip it or try again."),
            severity = ErrorSeverity.Warning,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction.Custom(
                actionId = "skip_video",
                label = UiMessage.Plain("Skip Video")
            ),
            secondaryAction = ErrorAction.Retry
        )
    }

    /**
     * Demonstrates a custom navigation route that's specific to the app.
     * This shows how to use ErrorNavigation.Custom to navigate to
     * app-specific routes or deep links.
     */
    fun showCustomNavigationExample() {
        _error.value = ActionableException(
            id = "payment_failed",
            msg = UiMessage.Plain("Payment processing failed. Please update your payment method."),
            severity = ErrorSeverity.Error,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction.Custom(
                actionId = "update_payment",
                label = UiMessage.Plain("Update Payment")
            ),
            secondaryAction = ErrorAction.Dismiss,
            navigation = ErrorNavigation.Custom("app://settings/payment-methods")
        )
    }

    /**
     * Demonstrates combining custom actions and custom navigation.
     * This is useful for complex error scenarios where the app needs
     * full control over both the action and navigation behavior.
     */
    fun showCustomActionAndNavigationExample() {
        _error.value = ActionableException(
            id = "subscription_expired",
            msg = UiMessage.Plain("Your subscription has expired. Renew now to continue enjoying premium features."),
            severity = ErrorSeverity.Warning,
            presentation = ErrorPresentation.FullScreen,
            primaryAction = ErrorAction.Custom(
                actionId = "renew_subscription",
                label = UiMessage.Plain("Renew Subscription")
            ),
            secondaryAction = ErrorAction.Custom(
                actionId = "view_plans",
                label = UiMessage.Plain("View Plans")
            ),
            navigation = ErrorNavigation.Custom("app://store/subscriptions?highlight=premium")
        )
    }

    /**
     * Demonstrates using custom input fields to create a fully customizable error.
     * Uses the values from the input fields for action ID, label, and navigation route.
     */
    fun showTestCustomInputs() {
        _error.value = ActionableException(
            id = "test_custom_inputs",
            msg = UiMessage.Plain("Test error with custom action and navigation from input fields"),
            severity = ErrorSeverity.Warning,
            presentation = ErrorPresentation.Dialog,
            primaryAction = ErrorAction.Custom(
                actionId = _customActionId.value,
                label = UiMessage.Plain(_customActionLabel.value)
            ),
            secondaryAction = ErrorAction.Dismiss,
            navigation = if (_customNavigationRoute.value.isNotBlank()) {
                ErrorNavigation.Custom(_customNavigationRoute.value)
            } else {
                null
            }
        )
    }

    // ========== v1.1.0 Feature: toActionableException() ==========

    /**
     * Demonstrates toActionableException() extension for error conversion.
     * v1.1.0: Shows how to use toActionableException() in catch blocks.
     */
    fun showToActionableExceptionDemo() {
        viewModelScope.launch {
            try {
                // Simulate an operation that throws an exception
                simulateFailingOperation()
            } catch (e: Exception) {
                // v1.1.0: Use toActionableException() for clean error conversion
                _error.value = e.toActionableException { throwable ->
                    // Custom mapping for specific exceptions
                    when (throwable) {
                        is SocketTimeoutException -> NetworkException(
                            msg = UiMessage.Plain("Connection timed out. Please check your internet."),
                            primaryAction = ErrorAction.Retry,
                            error = throwable
                        )
                        is IllegalStateException -> ActionableException(
                            id = "invalid_state",
                            msg = UiMessage.Plain("App is in an invalid state: ${throwable.message}"),
                            severity = ErrorSeverity.Error,
                            presentation = ErrorPresentation.Dialog,
                            primaryAction = ErrorAction.Ok
                        )
                        else -> null // Fall through to UnknownException
                    }
                }
            }
        }
    }

    /**
     * Demonstrates toActionableException() with no custom mapping (uses defaults).
     */
    fun showToActionableExceptionDefaultDemo() {
        viewModelScope.launch {
            try {
                throw RuntimeException("Something unexpected happened!")
            } catch (e: Exception) {
                // v1.1.0: Simple usage - falls back to UnknownException
                _error.value = e.toActionableException()
            }
        }
    }

    private fun simulateFailingOperation() {
        // Simulate a network timeout
        throw SocketTimeoutException("Connection timed out after 30000ms")
    }
}
