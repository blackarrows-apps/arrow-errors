# error-core

Core error handling infrastructure for Kotlin Multiplatform applications.

## Overview

The `error-core` module provides a comprehensive, type-safe error handling system that separates error definition from presentation. It allows you to define actionable exceptions with rich metadata about how they should be displayed and handled in the UI.

## Key Features

- **Actionable Exceptions**: Exceptions that carry metadata about how they should be presented and what actions users can take
- **UI Presentation Control**: Define whether errors should be shown as snackbars, dialogs, full-screen errors, or silently logged
- **Severity Levels**: Categorize errors by severity (Info, Warning, Error, Critical)
- **Error Actions**: Attach primary and secondary actions (Retry, Dismiss, etc.) to errors
- **Navigation Hints**: Optionally specify navigation targets after error acknowledgment
- **Error Reporting**: Pluggable error reporting system for logging and analytics
- **Error Mapping**: Utilities to map standard exceptions to domain-specific actionable exceptions

## Installation

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.blackarrows.errors:error-core:$version")
}
```

## Core Concepts

### ActionableException

The base class for all actionable exceptions in your application. It extends `RuntimeException` and adds rich metadata:

```kotlin
throw NetworkException(
    id = "network_error",
    msg = UiMessage.Plain("Unable to connect. Please check your internet connection."),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Snackbar,
    primaryAction = ErrorAction.Retry
)
```

### UiMessage

Flexible message representation supporting:
- **Plain**: Simple text messages
- **ResourceId**: Localized string resources
- **Formatted**: Templates with dynamic arguments

```kotlin
// Plain message
UiMessage.Plain("Network error occurred")

// Resource-based for localization
UiMessage.ResourceId("error_network")

// Formatted with arguments
UiMessage.Formatted("Failed to load {0} items", listOf(5))
```

### ErrorSeverity

Categorize errors by impact:
- **Info**: Non-critical notifications
- **Warning**: Recoverable issues
- **Error**: Operation failures
- **Critical**: Severe failures requiring immediate attention

### ErrorPresentation

Control how errors are displayed:
- **Snackbar**: Temporary notification at bottom of screen
- **Dialog**: Modal dialog requiring user interaction
- **FullScreen**: Full-screen error page
- **Silent**: Log only, no UI

### ErrorAction

Define user actions with predefined or custom types:
- **Retry**: Attempt the operation again
- **Dismiss**: Acknowledge and dismiss the error
- **Cancel**: Cancel the current operation
- **Ok**, **Close**, **Confirm**: Various acknowledgment actions
- **Delete**, **Remove**: Destructive actions
- **Custom**: Create app-specific actions with `ErrorAction.Custom(actionId, label)`

### ErrorNavigation

Suggest navigation after error acknowledgment:
- **Back**, **Home**, **Login**, **Settings**, **Support**, etc.
- **Custom**: Navigate to app-specific routes with `ErrorNavigation.Custom(route)`
- Extensible for custom navigation destinations

## Built-in Exception Types

### Network Exceptions

```kotlin
// Network connectivity error
NetworkException()

// Authentication failure
AuthException(navigation = ErrorNavigation.Login)

// Client error (4xx)
ClientException()

// Server error (5xx)
InternalException()

// JSON parsing error
JsonDecodingException()

// Content encoding error
ContentDecodingException()

// Empty response
EmptyResponseException()

// Unexpected status code
UnexpectedStatusException()

// Unsupported content type
UnsupportedMediaTypeException()
```

### Storage Exceptions

```kotlin
StorageException(
    msg = UiMessage.Plain("Unable to save data. Please check storage capacity."),
    primaryAction = ErrorAction.Retry
)
```

### Session Exceptions

```kotlin
SessionFailedException(
    severity = ErrorSeverity.Critical,
    navigation = ErrorNavigation.Login
)
```

### Other Exceptions

```kotlin
EmptyListException(
    msg = UiMessage.Plain("No items available."),
    severity = ErrorSeverity.Warning
)
```

## Error Mapping

Map standard exceptions to actionable exceptions:

```kotlin
try {
    makeHttpRequest()
} catch (e: Throwable) {
    throw e.mapError(
        isNetwork = true,
        default = { UnknownException(error = it) }
    )
}
```

The `mapError` extension function:
- Passes through known `ActionableException` types unchanged
- Maps `IOException` to `NetworkException` or `StorageException` based on `isNetwork` flag
- Preserves `CancellationException` for proper coroutine cancellation
- Uses the `default` factory for unknown exceptions

## toActionableException() Extension

For simpler error conversion in catch blocks, use the `toActionableException()` extension:

```kotlin
import io.blackarrows.errors.extensions.toActionableException

// Simple usage - falls back to UnknownException
try {
    performOperation()
} catch (e: Exception) {
    _error.value = e.toActionableException()
}

// With custom mapping
try {
    api.fetchData()
} catch (e: Exception) {
    _error.value = e.toActionableException { throwable ->
        when (throwable) {
            is SocketTimeoutException -> NetworkException(
                msg = UiMessage.Plain("Connection timed out"),
                error = throwable
            )
            is HttpException -> when (throwable.code) {
                401 -> AuthException()
                404 -> NotFoundException()
                else -> null  // Fall through to UnknownException
            }
            else -> null  // Fall through to UnknownException
        }
    }
}
```

**Differences from `mapError()`:**
- `mapError()` - Structured mapping with `isNetwork` flag, returns `Throwable`, designed for error translation pipelines
- `toActionableException()` - Simple utility for catch blocks, always returns `ActionableException`, designed for quick conversion

**Behavior:**
1. Rethrows `CancellationException` to preserve coroutine cancellation
2. Passes through existing `ActionableException` unchanged
3. Applies custom mapper if provided
4. Falls back to `UnknownException` if no mapping applies

## CommonActionIds

Type-safe constants for action IDs, useful in `when` blocks:

```kotlin
import io.blackarrows.errors.base.CommonActionIds

ErrorPresenter(
    error = error,
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId ->
        when (actionId) {
            CommonActionIds.RETRY -> viewModel.retry()
            CommonActionIds.DISMISS -> viewModel.clearError()
            CommonActionIds.LOGIN -> navController.navigate("login")
            CommonActionIds.CONTACT_SUPPORT -> openSupportChat()
            else -> { /* handle custom actions */ }
        }
    },
    onNavigate = { /* ... */ }
)
```

**Available constants:**
| Constant | Value | Maps to |
|----------|-------|---------|
| `RETRY` | `"retry"` | `ErrorAction.Retry` |
| `DISMISS` | `"dismiss"` | `ErrorAction.Dismiss` |
| `CANCEL` | `"cancel"` | `ErrorAction.Cancel` |
| `CLOSE` | `"close"` | `ErrorAction.Close` |
| `OK` | `"ok"` | `ErrorAction.Ok` |
| `CONFIRM` | `"confirm"` | `ErrorAction.Confirm` |
| `DELETE` | `"delete"` | `ErrorAction.Delete` |
| `REMOVE` | `"remove"` | `ErrorAction.Remove` |
| `GO_BACK` | `"go_back"` | - |
| `LOGIN` | `"login"` | - |
| `REFRESH` | `"refresh"` | - |
| `CONTACT_SUPPORT` | `"contact_support"` | - |

## CommonRoutes and suggestedRoute()

Simplify navigation handling with route constants and automatic mapping:

```kotlin
import io.blackarrows.errors.base.CommonRoutes
import io.blackarrows.errors.base.suggestedRoute

// Define routes in your NavHost
NavHost(navController, startDestination = CommonRoutes.HOME) {
    composable(CommonRoutes.HOME) { HomeScreen() }
    composable(CommonRoutes.LOGIN) { LoginScreen() }
    composable(CommonRoutes.SETTINGS) { SettingsScreen() }
}

// Handle navigation with suggestedRoute()
ErrorPresenter(
    error = error,
    onDismiss = { viewModel.clearError() },
    onActionClick = { /* ... */ },
    onNavigate = { navigation ->
        when (navigation) {
            ErrorNavigation.Back -> navController.popBackStack()
            null -> { /* no navigation */ }
            else -> navigation.suggestedRoute()?.let { route ->
                navController.navigate(route)
            }
        }
    }
)
```

**Available route constants:**
| Constant | Value |
|----------|-------|
| `CommonRoutes.LOGIN` | `"login"` |
| `CommonRoutes.HOME` | `"home"` |
| `CommonRoutes.SETTINGS` | `"settings"` |
| `CommonRoutes.SIGNUP` | `"signup"` |
| `CommonRoutes.PROFILE` | `"profile"` |
| `CommonRoutes.HELP` | `"help"` |
| `CommonRoutes.SUPPORT` | `"support"` |
| `CommonRoutes.STORE` | `"store"` |

**`suggestedRoute()` mapping:**
| ErrorNavigation | Returns |
|-----------------|---------|
| `ErrorNavigation.Login` | `CommonRoutes.LOGIN` |
| `ErrorNavigation.Home` | `CommonRoutes.HOME` |
| `ErrorNavigation.Settings` | `CommonRoutes.SETTINGS` |
| `ErrorNavigation.Custom(route)` | `route` |
| `ErrorNavigation.Back` | `null` |

## Error Reporting

Register error reporters for logging and analytics:

```kotlin
// Define a reporter
class FirebaseErrorReporter : ErrorReporter {
    override fun report(error: ActionableException) {
        Firebase.crashlytics.recordException(error)
        Analytics.logEvent("error_shown", mapOf(
            "error_id" to error.id,
            "severity" to error.severity.name
        ))
    }
}

// Register at app startup
ErrorReporting.addReporter(FirebaseErrorReporter())
ErrorReporting.addReporter(CustomAnalyticsReporter())

// Errors will be automatically reported to all registered reporters
throw NetworkException(...)
```

The reporting system:
- Supports multiple reporters
- Silently handles reporter failures to prevent cascading errors
- Thread-safe for concurrent access

## Usage in ViewModels

```kotlin
class MyViewModel(
    private val repository: MyRepository
) : ViewModel() {

    fun loadData() = viewModelScope.launch {
        try {
            val data = repository.fetchData()
            _state.value = State.Success(data)
        } catch (e: ActionableException) {
            when (e.presentation) {
                ErrorPresentation.Snackbar -> showSnackbar(e.msg)
                ErrorPresentation.Dialog -> showErrorDialog(e)
                ErrorPresentation.FullScreen -> navigateToErrorScreen(e)
                ErrorPresentation.Silent -> logError(e)
            }

            // Handle navigation if specified
            e.navigation?.let { destination ->
                navigate(destination)
            }
        }
    }
}
```

## Creating Custom Exceptions

```kotlin
data class PaymentFailedException(
    override val id: String? = "payment_failed",
    override val msg: UiMessage = UiMessage.Plain("Payment failed. Please try again."),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val secondaryAction: ErrorAction? = ErrorAction.Dismiss,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    secondaryAction = secondaryAction,
    error = error
)
```

## Creating Custom Actions

The library provides two approaches for custom actions:

### Approach 1: Using ErrorAction.Custom (Recommended)

For dynamic, app-specific actions:

```kotlin
throw VideoException(
    id = "video_playback_error",
    msg = UiMessage.Plain("Unable to play this video"),
    severity = ErrorSeverity.Warning,
    presentation = ErrorPresentation.Dialog,
    primaryAction = ErrorAction.Custom(
        actionId = "skip_video",
        label = UiMessage.Plain("Skip Video")
    ),
    secondaryAction = ErrorAction.Retry
)

// Handle in UI
when (actionId) {
    "skip_video" -> skipToNextVideo()
    "retry" -> retryPlayback()
}
```

### Approach 2: Extending ErrorAction

For reusable, type-safe actions:

```kotlin
object CustomActions {
    val ContactSupport = ErrorAction(
        actionId = "contact_support",
        label = UiMessage.Plain("Contact Support"),
        isDestructive = false
    )
}

throw ServiceException(
    primaryAction = CustomActions.ContactSupport
)
```

## Creating Custom Navigation

The library provides two approaches for custom navigation:

### Approach 1: Using ErrorNavigation.Custom (Recommended)

For dynamic routing with parameters:

```kotlin
throw PaymentException(
    id = "payment_failed",
    msg = UiMessage.Plain("Payment processing failed"),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Dialog,
    primaryAction = ErrorAction.Custom(
        actionId = "update_payment",
        label = UiMessage.Plain("Update Payment Method")
    ),
    navigation = ErrorNavigation.Custom("app://settings/payment-methods?retry=true")
)

// Handle in UI
when (val nav = error.navigation) {
    is ErrorNavigation.Custom -> navController.navigate(nav.route)
    ErrorNavigation.Login -> navController.navigate("login")
    ErrorNavigation.Back -> navController.popBackStack()
}
```

### Approach 2: Extending ErrorNavigation

For type-safe, reusable navigation destinations:

```kotlin
object AppNavigation {
    val Dashboard = ErrorNavigation()
    val Checkout = ErrorNavigation()
}

throw OrderException(
    navigation = AppNavigation.Checkout
)

// Handle in UI
when (navigation) {
    AppNavigation.Dashboard -> navController.navigate("dashboard")
    AppNavigation.Checkout -> navController.navigate("checkout")
}
```

## Platform Support

This module is fully multiplatform and supports:
- JVM (Android, Desktop)
- JavaScript (Browser, Node.js)
- Native (iOS, macOS, Linux, Windows)

## License

[Add your license here]
