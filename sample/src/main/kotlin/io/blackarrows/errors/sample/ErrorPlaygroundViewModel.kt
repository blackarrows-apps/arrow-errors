package io.blackarrows.errors.sample

import androidx.lifecycle.ViewModel
import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage
import io.blackarrows.errors.catalog.factories.*
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    init {
        // Set default locale
        DefaultMessageResolver.setLocale("en")
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

    fun handleAction(actionId: String) {
        _lastAction.value = "Action clicked: $actionId"
    }

    fun handleNavigation(navigation: ErrorNavigation?) {
        navigation?.let {
            _lastNavigation.value = "Navigation: ${it::class.simpleName}"
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
}
