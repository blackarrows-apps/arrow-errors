# Arrow Errors

A comprehensive Kotlin Multiplatform error-handling library that provides a scalable, type-safe approach to managing exceptions with rich UI presentation metadata and centralized error messaging.

## Overview

Arrow Errors helps you build consistent, user-friendly error experiences across your applications. It consists of two complementary modules:

- **error-core**: Rich, actionable exceptions with UI presentation metadata
- **error-catalog**: Centralized error messages with structured error codes

Instead of throwing generic exceptions with plain string messages, you can throw rich, actionable exceptions that carry all the information needed to present meaningful error dialogs to users, while maintaining consistent error messages across all platforms.

## Features

### error-core
- **Rich Error Metadata**: Each exception carries an ID, severity level, presentation mode, user-facing message, and actionable buttons
- **Type-Safe Actions**: Predefined error actions (Retry, Dismiss, Cancel, etc.) that can be extended by consumers
- **Flexible Navigation**: Platform-agnostic navigation directives (Login, Settings, Home, etc.) that can be customized
- **UI Message System**: Support for plain text, resource IDs, and formatted messages with arguments
- **Error Reporting**: Pluggable error reporting system for logging and analytics integration
- **Error Mapping**: Utilities to map standard exceptions to domain-specific actionable exceptions
- **Extensible Design**: Open class architecture allows library consumers to add custom actions and navigation types

### error-catalog
- **Centralized Error Messages**: Single source of truth for all error messages across platforms
- **Structured Error Codes**: 5-digit hierarchical error codes (CC-SSS format)
- **Cross-Platform Consistency**: Identical error messages on Android, iOS, and all other platforms
- **Fast Lookup**: O(1) error message lookup by error code
- **Type-Safe**: Compile-time safety with sealed interfaces
- **Serializable**: Error catalog entries can be serialized for network transport

### General
- **Kotlin Multiplatform**: Works across all Kotlin platforms (JVM, Android, iOS, JS, Native)
- **Modular Architecture**: Use only what you need - error-core and error-catalog work independently or together
- **Production Ready**: Comprehensive KDocs, detailed READMEs, and tested across platforms

## Core Concepts

### ActionableException

The base exception class that all errors extend. It includes:

```kotlin
open class ActionableException(
    open val id: String? = null,                      // Unique ID for deduplication/logging
    open val msg: UiMessage = ...,                     // User-facing message
    open val severity: ErrorSeverity = ...,            // Info, Warning, Error, Critical
    open val presentation: ErrorPresentation = ...,    // Snackbar, Dialog, FullScreen, Silent
    open val primaryAction: ErrorAction? = null,       // Primary button action
    open val secondaryAction: ErrorAction? = null,     // Secondary button action
    open val navigation: ErrorNavigation? = null,      // Optional navigation directive
    open val error: Throwable? = null,                 // Underlying cause
)
```

### UiMessage

Flexible message representation:

```kotlin
sealed interface UiMessage {
    data class Plain(val text: String)
    data class ResourceId(val resId: String)
    data class Formatted(val template: String, val args: List<Any>)
}
```

### ErrorSeverity

Severity levels for errors:
- `Info` - Informational messages
- `Warning` - Non-critical issues
- `Error` - Standard errors
- `Critical` - Severe errors requiring immediate attention

### ErrorPresentation

How the error should be displayed:
- `Snackbar` - Brief, dismissible notification
- `Dialog` - Modal dialog
- `FullScreen` - Full-screen error state
- `Silent` - Logged but not shown to user

### ErrorAction

Predefined actions that consumers can use or extend:

```kotlin
open class ErrorAction(
    open val label: UiMessage,
    open val isDestructive: Boolean = false
)

// Predefined actions
ErrorAction.Retry
ErrorAction.Dismiss
ErrorAction.Cancel
ErrorAction.Close
ErrorAction.Ok
ErrorAction.Confirm
ErrorAction.Delete  // isDestructive = true
ErrorAction.Remove  // isDestructive = true
```

### ErrorNavigation

Platform-agnostic navigation directives:

```kotlin
open class ErrorNavigation

// Predefined navigation types
ErrorNavigation.Back
ErrorNavigation.Home
ErrorNavigation.Login
ErrorNavigation.Signup
ErrorNavigation.Settings
ErrorNavigation.Profile
ErrorNavigation.Help
ErrorNavigation.Support
ErrorNavigation.Store
```

## Usage

### Basic Exception

```kotlin
throw NetworkException(
    id = "network_timeout",
    msg = UiMessage.Plain("Unable to connect to server"),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Snackbar,
    primaryAction = ErrorAction.Retry,
    error = originalException
)
```

### Exception with Navigation

```kotlin
throw AuthException(
    id = "auth_failed",
    msg = UiMessage.Plain("Your session has expired"),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Dialog,
    primaryAction = ErrorAction.Retry,
    navigation = ErrorNavigation.Login,
    error = authError
)
```

### Custom Actions

Library consumers can extend the action system:

```kotlin
object CustomActions {
    val ContactSupport = ErrorAction(
        label = UiMessage.Plain("Contact Support"),
        isDestructive = false
    )
}

throw MyException(
    primaryAction = CustomActions.ContactSupport
)
```

### Custom Navigation

Consumers can add their own navigation types:

```kotlin
object AppNavigation {
    val Dashboard = ErrorNavigation()
    val Profile = ErrorNavigation()
}

// Or with custom data
class RouteNavigation(val route: String) : ErrorNavigation()

throw MyException(
    navigation = AppNavigation.Dashboard
)
```

## Handling Errors in UI

Your UI layer can pattern match on exceptions to present them appropriately:

```kotlin
try {
    // Your code
} catch (e: ActionableException) {
    when (e.presentation) {
        ErrorPresentation.Dialog -> showDialog(
            message = e.msg.toDisplayString(),
            primaryButton = e.primaryAction,
            secondaryButton = e.secondaryAction
        )
        ErrorPresentation.Snackbar -> showSnackbar(
            message = e.msg.toDisplayString(),
            action = e.primaryAction
        )
        ErrorPresentation.FullScreen -> showErrorScreen(e)
        ErrorPresentation.Silent -> logger.error(e)
    }

    e.navigation?.let { nav ->
        when (nav) {
            ErrorNavigation.Login -> navigateToLogin()
            ErrorNavigation.Back -> navigateBack()
            ErrorNavigation.Home -> navigateToHome()
            // etc.
        }
    }
}
```

## Included Exception Types

The library includes common exception types:

### Network Exceptions
- `NetworkException` - General network errors
- `AuthException` - Authentication failures
- `ClientException` - Client-side errors (4xx)
- `InternalException` - Server errors (5xx)
- `UnexpectedStatusException` - Unexpected HTTP status
- `EmptyResponseException` - Empty response body
- `JsonDecodingException` - JSON parsing errors
- `ContentDecodingException` - Content decoding errors
- `UnsupportedMediaTypeException` - Unsupported content types
- `StorageException` - Storage/disk errors
- `UnknownException` - Unknown errors

### Session Exceptions
- `SessionFailedException` - Session validation failures

### Other Exceptions
- `EmptyListException` - Empty list/data states

## Modules

### error-core

The core error handling infrastructure with rich exception types and metadata.

[View error-core README](error-core/README.md)

```kotlin
dependencies {
    implementation("io.blackarrows.errors:error-core:$version")
}
```

### error-catalog

Centralized error message catalog with structured error codes.

[View error-catalog README](error-catalog/README.md)

```kotlin
dependencies {
    implementation("io.blackarrows.errors:error-catalog:$version")
}
```

### Using Both Together

For the complete error-handling solution:

```kotlin
dependencies {
    implementation("io.blackarrows.errors:error-core:$version")
    implementation("io.blackarrows.errors:error-catalog:$version")
}
```

## Quick Start

### Basic Error Handling (error-core)

```kotlin
// Throw a rich, actionable exception
throw NetworkException(
    id = "network_error",
    msg = UiMessage.Plain("Unable to connect. Please check your internet connection."),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Snackbar,
    primaryAction = ErrorAction.Retry
)

// Handle in UI
try {
    fetchData()
} catch (e: ActionableException) {
    when (e.presentation) {
        ErrorPresentation.Snackbar -> showSnackbar(e.msg)
        ErrorPresentation.Dialog -> showDialog(e)
        ErrorPresentation.FullScreen -> showErrorScreen(e)
        ErrorPresentation.Silent -> logError(e)
    }
}
```

### Centralized Error Messages (error-catalog)

```kotlin
// Access error catalog entries
val error = NetworkErrorCatalog.Unavailable
println(error.errorCode)  // 10000
println(error.message)    // "Network is unavailable. Please check your connection."

// Use with ErrorProvider for dynamic lookup
class MyViewModel(
    private val errorProvider: ErrorProvider
) {
    fun handleError(errorCode: Int) {
        val message = errorProvider.getErrorMessage(errorCode)
        showError(message)
    }
}
```

### Combining Both Modules

```kotlin
// Use catalog errors with actionable exceptions
val catalogError = SessionErrorCatalog.LoginInvalidCredentials

throw AuthException(
    id = catalogError.errorCode.toString(),
    msg = UiMessage.Plain(catalogError.message),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Dialog,
    navigation = ErrorNavigation.Login
)
```

## Error Reporting

Register error reporters for logging and analytics:

```kotlin
// Define a custom reporter
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
```

## Platform Support

Both modules are fully multiplatform and support:
- **JVM** (Android, Desktop)
- **JavaScript** (Browser, Node.js)
- **Native** (iOS, macOS, Linux, Windows)

## Documentation

- [error-core README](error-core/README.md) - Comprehensive guide to actionable exceptions
- [error-catalog README](error-catalog/README.md) - Complete error catalog documentation
- [API Documentation](#) - KDoc-generated API reference (coming soon)

## Architecture

```
arrow-errors/
├── error-core/           # Rich exception types with UI metadata
│   ├── base/            # Core types: ActionableException, ErrorAction, etc.
│   ├── network/         # Network-related exceptions
│   ├── session/         # Session-related exceptions
│   ├── mappers/         # Error mapping utilities
│   └── README.md
│
└── error-catalog/       # Centralized error messages
    ├── ErrorCatalog.kt         # Base interface
    ├── NetworkErrorCatalog.kt  # Network errors (10-XXX)
    ├── StorageErrorCatalog.kt  # Storage errors (11-XXX)
    ├── AuthErrorCatalog.kt     # Auth errors (12-XXX)
    ├── SessionErrorCatalog.kt  # Session errors (20-XXX)
    ├── ErrorProvider.kt        # Error lookup interface
    ├── DefaultErrorProvider.kt # Default implementation
    └── README.md
```

## License

[Add your license here]

## Contributing

[Add contribution guidelines here]
