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

Define user actions:
- **Retry**: Attempt the operation again
- **Dismiss**: Acknowledge and dismiss the error
- **Navigate(destination)**: Navigate to a specific screen
- Custom actions can be defined by extending `ErrorAction`

### ErrorNavigation

Suggest navigation after error acknowledgment:
- **Back**, **Home**, **Login**, **Settings**, etc.
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

## Creating Custom Navigation

```kotlin
class MyCustomNavigation : ErrorNavigation() {
    companion object {
        val Dashboard = MyCustomNavigation()
        val Checkout = MyCustomNavigation()
    }
}

// Use in exceptions
throw PaymentFailedException(
    navigation = MyCustomNavigation.Checkout
)
```

## Creating Custom Actions

```kotlin
class ContactSupport : ErrorAction() {
    companion object {
        val Default = ContactSupport()
    }
}

throw ServiceException(
    primaryAction = ContactSupport.Default
)
```

## Platform Support

This module is fully multiplatform and supports:
- JVM (Android, Desktop)
- JavaScript (Browser, Node.js)
- Native (iOS, macOS, Linux, Windows)

## License

[Add your license here]
