# Arrow Errors

A Kotlin Multiplatform error-handling library that provides a scalable, type-safe approach to managing exceptions with rich UI presentation metadata.

## Overview

Arrow Errors helps you build consistent, user-friendly error experiences across your applications. Instead of throwing generic exceptions with plain string messages, you can throw rich, actionable exceptions that carry all the information needed to present meaningful error dialogs to users.

## Features

- **Rich Error Metadata**: Each exception carries an ID, severity level, presentation mode, user-facing message, and actionable buttons
- **Type-Safe Actions**: Predefined error actions (Retry, Dismiss, Cancel, etc.) that can be extended by consumers
- **Flexible Navigation**: Platform-agnostic navigation directives (Login, Settings, Home, etc.) that can be customized
- **UI Message System**: Support for plain text, resource IDs, and formatted messages with arguments
- **Extensible Design**: Open class architecture allows library consumers to add custom actions and navigation types
- **Kotlin Multiplatform**: Works across all Kotlin platforms (JVM, Android, iOS, JS, Native)

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

## Installation

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.blackarrows:arrow-errors:1.0.0")
}
```

## License

[Add your license here]

## Contributing

[Add contribution guidelines here]
