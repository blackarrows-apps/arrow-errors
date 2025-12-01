# Arrow Errors

> A comprehensive Kotlin Multiplatform error-handling library that provides a scalable, type-safe approach to managing exceptions with rich UI presentation metadata and centralized error messaging.

**Author & Maintainer:** [Emmanuel Conradie](https://github.com/E5c11)

## Introduction

Arrow Errors helps you build consistent, user-friendly error experiences across your Kotlin Multiplatform applications. Born from the need to standardize error handling across platforms, this library transforms the way you think about exceptions—from simple error messages to rich, actionable user experiences.

Instead of throwing generic exceptions with plain string messages, Arrow Errors enables you to throw rich, actionable exceptions that carry all the information needed to present meaningful error dialogs to users. With built-in internationalization support, centralized error messaging, and ready-to-use UI components, you can create professional error experiences with minimal boilerplate.

The library consists of three complementary modules that work independently or together:

- **error-core**: Rich, actionable exceptions with UI presentation metadata
- **error-catalog**: Centralized error messages with structured error codes and i18n support (English, Spanish, French)
- **error-compose**: Compose Multiplatform UI components for displaying errors

Whether you're building an Android app, iOS application, or desktop software, Arrow Errors provides a unified approach to error handling that scales with your project.

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
- **Internationalization (i18n)**: Built-in support for multiple languages with English, Spanish, and French translations
- **Structured Error Codes**: 5-digit hierarchical error codes (CC-SSS format)
- **Cross-Platform Consistency**: Identical error messages on Android, iOS, and all other platforms
- **Exception Factory Functions**: Convenient builders for creating exceptions with i18n support
- **Fast Lookup**: O(1) error message lookup by error code
- **Type-Safe**: Compile-time safety with sealed interfaces
- **Serializable**: Error catalog entries can be serialized for network transport
- **Extensible**: Easy to add new languages or integrate with custom i18n systems

### error-compose
- **Ready-to-Use Components**: Material 3 components (Dialog, Snackbar, FullScreen) for displaying errors
- **Smart Routing**: ErrorPresenter automatically selects the right UI based on ErrorPresentation
- **Theme Inheritance**: Components automatically match your app's MaterialTheme
- **Composable Slots**: Fully customizable via composable slots for icon, title, content, and actions
- **i18n Integration**: Seamless integration with error-catalog's message resolution
- **Severity-Based Styling**: Automatic icon, color, and duration based on ErrorSeverity
- **Extensible**: Easy to build custom error components or override defaults
- **Accessibility**: Semantic properties for screen readers (coming soon)

### General
- **Kotlin Multiplatform**: Works across all Kotlin platforms (JVM, Android, iOS, JS, Native)
- **Modular Architecture**: Use only what you need - modules work independently or together
- **Production Ready**: Comprehensive KDocs, detailed READMEs, and tested across platforms

## Installation

[![Maven Central](https://img.shields.io/maven-central/v/io.github.blackarrows-apps/arrow-errors-core)](https://central.sonatype.com/search?q=io.github.blackarrows-apps+arrow-errors)
[![Latest Release](https://img.shields.io/github/v/release/blackarrows-apps/arrow-errors)](https://github.com/blackarrows-apps/arrow-errors/releases)

Arrow Errors is published to Maven Central. Add the dependencies you need to your Kotlin Multiplatform project.

> **Latest Version:** Check the [releases page](https://github.com/blackarrows-apps/arrow-errors/releases) for the current version.

### Using All Modules (Recommended)

For the complete error-handling solution with UI components:

```kotlin
// In your build.gradle.kts
// Replace $version with the latest version from releases
dependencies {
    implementation("io.github.blackarrows-apps:arrow-errors-core:$version")
    implementation("io.github.blackarrows-apps:arrow-errors-catalog:$version")
    implementation("io.github.blackarrows-apps:arrow-errors-compose:$version")
}
```

### Individual Modules

Choose only what you need:

**error-core** - Core error handling infrastructure:
```kotlin
dependencies {
    implementation("io.github.blackarrows-apps:arrow-errors-core:$version")
}
```

**error-catalog** - Centralized error messages with i18n:
```kotlin
dependencies {
    implementation("io.github.blackarrows-apps:arrow-errors-catalog:$version")
}
```

**error-compose** - Compose Multiplatform UI components:
```kotlin
dependencies {
    implementation("io.github.blackarrows-apps:arrow-errors-compose:$version")
}
```

### Supported Platforms

- **Android** (minSdk 21+)
- **iOS** (iOS 14+)
- **JVM** (Java 11+)
- **JavaScript** (Browser, Node.js)
- **Native** (Linux, macOS, Windows)

## Try It Out

Want to see Arrow Errors in action? Install the interactive Android playground:

```bash
./gradlew :sample:installDebug
```

The sample app demonstrates all error types, presentations, and i18n features with a comprehensive UI playground. See the [sample README](sample/README.md) for more details.

## Quick Start

Get up and running in minutes with these examples:

### 1. Basic Error Handling (error-core only)

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

### 2. With Centralized Messages (error-core + error-catalog)

```kotlin
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.factories.*

// Set locale once during app initialization
DefaultMessageResolver.setLocale("es")  // Spanish

// Use exception factory functions (messages automatically in Spanish)
throw networkException(
    error = IOException("Connection failed")
)
// Message: "La red no está disponible. Por favor, verifica tu conexión."
```

### 3. Complete Solution with UI (all modules)

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val error by viewModel.error.collectAsState()

    Scaffold { padding ->
        MyScreenContent(modifier = Modifier.padding(padding))

        // ErrorPresenter automatically displays the right UI
        ErrorPresenter(
            error = error,
            onDismiss = { viewModel.clearError() },
            onActionClick = { actionId ->
                when (actionId) {
                    "retry" -> viewModel.retry()
                    "dismiss" -> viewModel.clearError()
                }
            },
            onNavigate = { navigation ->
                when (navigation) {
                    ErrorNavigation.Login -> navController.navigate("login")
                    ErrorNavigation.Back -> navController.popBackStack()
                    else -> {}
                }
            }
        )
    }
}
```

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

Predefined actions with support for custom app-specific actions:

```kotlin
open class ErrorAction(
    open val actionId: String,
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

// Custom actions for app-specific use cases
ErrorAction.Custom(
    actionId = "skip_video",
    label = UiMessage.Plain("Skip Video")
)
```

### ErrorNavigation

Platform-agnostic navigation directives with support for custom routes:

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

// Custom navigation for app-specific routes
ErrorNavigation.Custom(route = "app://settings/payment")
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

The library provides two ways to create custom actions:

**1. Using ErrorAction.Custom (Recommended)**

```kotlin
throw VideoException(
    id = "video_playback_error",
    msg = UiMessage.Plain("Unable to play video"),
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
    "skip_video" -> viewModel.skipVideo()
    "retry" -> viewModel.retry()
}
```

**2. Extending ErrorAction**

```kotlin
object CustomActions {
    val ContactSupport = ErrorAction(
        actionId = "contact_support",
        label = UiMessage.Plain("Contact Support"),
        isDestructive = false
    )
}

throw MyException(
    primaryAction = CustomActions.ContactSupport
)
```

### Custom Navigation

The library provides two ways to create custom navigation:

**1. Using ErrorNavigation.Custom (Recommended)**

```kotlin
throw PaymentException(
    id = "payment_failed",
    msg = UiMessage.Plain("Payment processing failed"),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Dialog,
    primaryAction = ErrorAction.Custom(
        actionId = "update_payment",
        label = UiMessage.Plain("Update Payment")
    ),
    navigation = ErrorNavigation.Custom("app://settings/payment-methods")
)

// Handle in UI
when (navigation) {
    is ErrorNavigation.Custom -> navController.navigate(navigation.route)
    ErrorNavigation.Login -> navController.navigate("login")
    ErrorNavigation.Back -> navController.popBackStack()
}
```

**2. Extending ErrorNavigation**

```kotlin
object AppNavigation {
    val Dashboard = ErrorNavigation()
    val Profile = ErrorNavigation()
}

throw MyException(
    navigation = AppNavigation.Dashboard
)

// Handle in UI
when (navigation) {
    AppNavigation.Dashboard -> navController.navigate("dashboard")
    AppNavigation.Profile -> navController.navigate("profile")
}
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

## Module Documentation

Each module has comprehensive documentation with detailed examples:

- **[error-core README](error-core/README.md)** - Complete guide to rich, actionable exceptions
- **[error-catalog README](error-catalog/README.md)** - Error catalog, i18n, and factory functions
- **[error-compose README](error-compose/README.md)** - UI components for Compose Multiplatform
- **[sample README](sample/README.md)** - Interactive playground demonstrating all features

## Internationalization (i18n)

The error-catalog module includes **built-in internationalization support** with translations for multiple languages.

### Supported Languages

- **English (en)** - Default
- **Spanish (es)**
- **French (fr)**

### Quick Start with i18n

```kotlin
import io.blackarrows.errors.catalog.i18n.DefaultMessageResolver
import io.blackarrows.errors.catalog.factories.*

// 1. Set locale once during app initialization
DefaultMessageResolver.setLocale("es")  // Spanish

// 2. Use exception factory functions
throw networkException(
    error = IOException("Connection failed")
)
// Error message will automatically be in Spanish:
// "La red no está disponible. Por favor, verifica tu conexión."
```

### Exception Factory Functions

The catalog provides convenient factory functions that automatically use the configured locale:

```kotlin
// Generic factories (use default error messages)
networkException()       // Network error
authException()         // Auth error
storageException()      // Storage error
sessionFailedException() // Session error

// Specific factories (use specific error messages)
networkUnavailableException()  // "Network is unavailable..."
networkTimeoutException()      // "Request timed out..."
unauthorizedException()        // "Your session has expired..."
tokenExpiredException()        // "Your session has expired..."
forbiddenException()           // "You don't have permission..."
storageUnavailableException()  // "Storage is unavailable..."
insufficientSpaceException()   // "Insufficient storage space..."
invalidSessionException()      // "Session data is invalid..."
```

### Overriding Defaults

All factory function parameters can be overridden:

```kotlin
// Override message (use custom text instead of i18n)
throw networkException(
    msg = UiMessage.Plain("Custom network error"),
    error = IOException()
)

// Override actions
throw authException(
    primaryAction = ErrorAction.Dismiss,
    secondaryAction = CustomActions.ContactSupport,
    error = authError
)

// Override navigation
throw authException(
    navigation = CustomNavigation.Onboarding,
    error = authError
)

// Override presentation
throw networkException(
    presentation = ErrorPresentation.Dialog,  // Show as dialog instead of snackbar
    error = IOException()
)

// Override everything
throw networkException(
    id = "custom_network_error",
    msg = UiMessage.Plain("Completely custom"),
    severity = ErrorSeverity.Warning,
    presentation = ErrorPresentation.Silent,
    primaryAction = CustomActions.ContactSupport,
    error = error
)
```

### Custom Message Resolver

Integrate with your own i18n system:

```kotlin
// Android Resources example
class AndroidResourceResolver(private val context: Context) : MessageResolver {
    override fun resolve(key: String): String {
        val resId = context.resources.getIdentifier(key, "string", context.packageName)
        return if (resId != 0) {
            context.getString(resId)
        } else {
            DefaultMessageResolver.resolve(key)  // Fallback
        }
    }

    override fun resolve(key: String, args: List<Any>): String {
        val resId = context.resources.getIdentifier(key, "string", context.packageName)
        return if (resId != 0) {
            context.getString(resId, *args.toTypedArray())
        } else {
            DefaultMessageResolver.resolve(key, args)
        }
    }
}

// Use in UI layer to resolve messages
when (val msg = exception.msg) {
    is UiMessage.Plain -> msg.text
    is UiMessage.ResourceId -> myResolver.resolve(msg.resId)
    is UiMessage.Formatted -> myResolver.resolve(msg.template, msg.args)
}
```

### Adding New Languages

See the [error-catalog README](error-catalog/README.md#adding-new-languages) for details on adding support for additional languages.

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

All modules are fully multiplatform and tested across:
- **JVM** (Android, Desktop)
- **JavaScript** (Browser, Node.js)
- **Native** (iOS, macOS, Linux, Windows)

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
├── error-catalog/       # Centralized error messages with i18n
│   ├── ErrorCatalog.kt         # Base interface
│   ├── NetworkErrorCatalog.kt  # Network errors (10-XXX)
│   ├── StorageErrorCatalog.kt  # Storage errors (11-XXX)
│   ├── AuthErrorCatalog.kt     # Auth errors (12-XXX)
│   ├── SessionErrorCatalog.kt  # Session errors (20-XXX)
│   ├── ErrorProvider.kt        # Error lookup interface
│   ├── DefaultErrorProvider.kt # Default implementation
│   ├── i18n/
│   │   ├── ErrorKeys.kt                 # Message key constants
│   │   ├── MessageResolver.kt           # Message resolution interface
│   │   ├── DefaultMessageResolver.kt    # Built-in resolver
│   │   └── translations/
│   │       ├── EnglishTranslations.kt   # English (default)
│   │       ├── SpanishTranslations.kt   # Spanish
│   │       └── FrenchTranslations.kt    # French
│   ├── factories/
│   │   └── ExceptionFactories.kt        # Convenience factory functions
│   └── README.md
│
└── error-compose/       # Compose Multiplatform UI components
    ├── components/
    │   ├── ErrorPresenter.kt    # Smart routing to correct UI
    │   ├── ErrorDialog.kt       # Material 3 error dialog
    │   ├── ErrorSnackbar.kt     # Material 3 snackbar
    │   └── ErrorFullScreen.kt   # Full-screen error state
    ├── utils/
    │   ├── MessageResolver.kt   # Message resolution helpers
    │   └── SeverityExtensions.kt # Severity-based styling
    └── README.md
```

## Contributing

Contributions are welcome! Whether you're fixing bugs, improving documentation, adding new features, or translating error messages to new languages, your help is appreciated.

### How to Contribute

1. **Fork the repository** on GitHub
2. **Clone your fork** locally
3. **Create a feature branch**: `git checkout -b feature/your-feature-name`
4. **Make your changes** and ensure tests pass
5. **Commit your changes**: `git commit -m "Description of changes"`
6. **Push to your fork**: `git push origin feature/your-feature-name`
7. **Open a Pull Request** with a clear description of your changes

### Areas for Contribution

- Adding translations for new languages (see [error-catalog README](error-catalog/README.md#adding-new-languages))
- Improving documentation and examples
- Adding new exception types for common use cases
- Enhancing UI components with new features
- Writing tests and improving code coverage
- Reporting bugs and suggesting features

### Development Setup

```bash
# Clone the repository
git clone https://github.com/E5c11/arrow-errors.git
cd arrow-errors

# Build all modules
./gradlew build

# Run tests
./gradlew test
```

For questions or discussions, please open an issue on GitHub.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

```
Copyright 2025 Emmanuel Conradie

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Support

If you find this library helpful, please consider:
- Starring the repository on GitHub
- Sharing it with others who might benefit
- Contributing improvements or translations
- Reporting issues and suggesting features

## Acknowledgments

Built with Kotlin Multiplatform and inspired by the need for better cross-platform error handling.

---

**Maintained by [Emmanuel Conradie](https://github.com/E5c11)**
