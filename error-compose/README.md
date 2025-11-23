# error-compose

Compose Multiplatform UI components for displaying actionable errors with Material 3 design.

## Overview

The error-compose module provides ready-to-use UI components that automatically display errors based on their presentation metadata. It seamlessly integrates with [error-core](../error-core) and [error-catalog](../error-catalog) to deliver a complete error handling and presentation solution.

## Features

- **Smart Error Routing**: `ErrorPresenter` automatically selects the right UI component based on `ErrorPresentation`
- **Material 3 Components**:
  - `ErrorDialog` - Modal dialogs for important errors
  - `ErrorSnackbar` - Brief notifications for non-critical errors
  - `ErrorFullScreen` - Full-screen states for critical errors
- **Automatic Styling**: Icons, colors, and durations based on `ErrorSeverity`
- **i18n Integration**: Seamless message resolution using error-catalog's `MessageResolver`
- **Fully Customizable**: All components support composable slots for custom content
- **Theme Inheritance**: Automatically matches your app's `MaterialTheme`
- **Type-Safe Actions**: Handles primary/secondary actions and navigation directives

## Installation

```kotlin
dependencies {
    implementation("io.blackarrows.errors:error-compose:1.0.0")

    // Required dependencies
    implementation("io.blackarrows.errors:error-core:1.0.0")
    implementation("io.blackarrows.errors:error-catalog:1.0.0")
}
```

## Quick Start

### Basic Usage with ErrorPresenter

The easiest way to display errors is using `ErrorPresenter`, which automatically routes to the correct UI component:

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val error by viewModel.error.collectAsState()

    Scaffold { padding ->
        MyScreenContent(modifier = Modifier.padding(padding))

        // ErrorPresenter handles all error presentations
        ErrorPresenter(
            error = error,
            onDismiss = { viewModel.clearError() },
            onActionClick = { actionId ->
                when (actionId) {
                    "retry" -> viewModel.retry()
                    "dismiss" -> viewModel.clearError()
                    else -> { /* handle other actions */ }
                }
            },
            onNavigate = { navigation ->
                navigation?.let {
                    when (it) {
                        ErrorNavigation.Login -> navController.navigate("login")
                        ErrorNavigation.Back -> navController.popBackStack()
                        ErrorNavigation.Home -> navController.navigate("home")
                        else -> { /* handle other navigation */ }
                    }
                }
            }
        )
    }
}
```

### ViewModel Setup

```kotlin
class MyViewModel : ViewModel() {
    private val _error = MutableStateFlow<ActionableException?>(null)
    val error = _error.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            try {
                // Your business logic
                val data = repository.fetchData()
            } catch (e: IOException) {
                // Use factory functions from error-catalog
                _error.value = networkException(error = e)
            } catch (e: Exception) {
                _error.value = ActionableException(
                    msg = UiMessage.Plain("An unexpected error occurred"),
                    severity = ErrorSeverity.Error,
                    presentation = ErrorPresentation.Snackbar,
                    primaryAction = ErrorAction.Retry
                )
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun retry() {
        clearError()
        loadData()
    }
}
```

## Components

### ErrorPresenter

Smart router that automatically displays the correct UI component based on `ErrorPresentation`:

- `ErrorPresentation.Dialog` → `ErrorDialog`
- `ErrorPresentation.Snackbar` → `ErrorSnackbar`
- `ErrorPresentation.FullScreen` → `ErrorFullScreen`
- `ErrorPresentation.Silent` → Logs only, no UI

```kotlin
ErrorPresenter(
    error = error,
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId -> viewModel.handleAction(actionId) },
    onNavigate = { nav -> viewModel.handleNavigation(nav) },
    resolver = DefaultMessageResolver  // Optional: custom resolver
)
```

### ErrorDialog

Material 3 alert dialog for important errors:

```kotlin
ErrorDialog(
    error = authException(error = authError),
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId ->
        when (actionId) {
            "retry" -> viewModel.retry()
            "dismiss" -> viewModel.clearError()
        }
    },
    onNavigate = { nav ->
        if (nav == ErrorNavigation.Login) {
            navController.navigate("login")
        }
    }
)
```

**Customization:**

```kotlin
ErrorDialog(
    error = error,
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId -> viewModel.handleAction(actionId) },
    onNavigate = { nav -> viewModel.handleNavigation(nav) },

    // Custom icon
    icon = {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = Color.Red
        )
    },

    // Custom title
    title = {
        Text("Payment Failed", style = MaterialTheme.typography.headlineSmall)
    },

    // Custom content
    content = {
        Column {
            Text("Your payment could not be processed.")
            Text("Card ending in ${cardLast4} was declined.")
        }
    },

    // Custom actions
    actions = {
        Row {
            TextButton(onClick = { navController.navigate("payment-methods") }) {
                Text("Change Card")
            }
            Button(onClick = { viewModel.retryPayment() }) {
                Text("Retry")
            }
        }
    }
)
```

### ErrorSnackbar

Material 3 snackbar for brief, non-intrusive notifications:

```kotlin
ErrorSnackbar(
    error = networkException(error = IOException()),
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId ->
        if (actionId == "retry") viewModel.retry()
    }
)
```

**Duration is automatic based on severity:**
- `Info` → Short (4 seconds)
- `Warning` → Long (10 seconds)
- `Error` → Long (10 seconds)
- `Critical` → Indefinite (until dismissed)

**Integration with Scaffold:**

```kotlin
val snackbarHostState = remember { SnackbarHostState() }

Scaffold(
    snackbarHost = {
        val error by viewModel.error.collectAsState()
        error?.let {
            ErrorSnackbar(
                error = it,
                onDismiss = { viewModel.clearError() },
                onActionClick = { actionId -> viewModel.handleAction(actionId) },
                snackbarHostState = snackbarHostState
            )
        }
    }
) { padding ->
    MyScreenContent(modifier = Modifier.padding(padding))
}
```

### ErrorFullScreen

Full-screen error state for critical errors:

```kotlin
val error by viewModel.error.collectAsState()

if (error?.presentation == ErrorPresentation.FullScreen) {
    ErrorFullScreen(
        error = error!!,
        onActionClick = { actionId -> viewModel.handleAction(actionId) },
        onNavigate = { nav -> viewModel.handleNavigation(nav) }
    )
} else {
    // Regular content
    MyScreenContent()
}
```

**Customization:**

```kotlin
ErrorFullScreen(
    error = error,
    onActionClick = { actionId -> viewModel.handleAction(actionId) },
    onNavigate = { nav -> viewModel.handleNavigation(nav) },

    backgroundColor = Color.Black,
    messageStyle = MaterialTheme.typography.headlineMedium,

    // Custom icon
    icon = {
        Icon(
            imageVector = Icons.Default.CloudOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(120.dp)
        )
    },

    // Custom content
    content = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Connection Lost",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please check your internet connection",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
)
```

## Severity-Based Styling

All components automatically apply styling based on `ErrorSeverity`:

| Severity | Icon | Color | Snackbar Duration |
|----------|------|-------|-------------------|
| Info | `Icons.Default.Info` | Blue | Short (4s) |
| Warning | `Icons.Default.Warning` | Orange | Long (10s) |
| Error | `Icons.Default.Error` | Red | Long (10s) |
| Critical | `Icons.Default.ErrorOutline` | Dark Red | Indefinite |

## Internationalization (i18n)

The error-compose module seamlessly integrates with error-catalog's i18n system:

### Using Default Resolver

```kotlin
// Set locale once during app initialization
DefaultMessageResolver.setLocale("es")

// All components automatically use the configured locale
ErrorPresenter(
    error = networkException(error = IOException()),
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId -> viewModel.handleAction(actionId) },
    onNavigate = { nav -> viewModel.handleNavigation(nav) }
)
// Error message will be in Spanish: "La red no está disponible..."
```

### Custom Message Resolver

For Android resource integration or custom i18n systems:

```kotlin
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

// Use in components
ErrorPresenter(
    error = error,
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId -> viewModel.handleAction(actionId) },
    onNavigate = { nav -> viewModel.handleNavigation(nav) },
    resolver = AndroidResourceResolver(context)
)
```

## Advanced Examples

### Multiple Error Types

```kotlin
@Composable
fun ComplexScreen(viewModel: ComplexViewModel) {
    val error by viewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (error?.presentation) {
                ErrorPresentation.FullScreen -> {
                    // Critical errors take over the entire screen
                    ErrorFullScreen(
                        error = error!!,
                        onActionClick = { actionId -> viewModel.handleAction(actionId) },
                        onNavigate = { nav -> viewModel.handleNavigation(nav) }
                    )
                }
                else -> {
                    // Regular content
                    MyScreenContent()

                    // ErrorPresenter handles Dialog and Snackbar
                    ErrorPresenter(
                        error = error,
                        onDismiss = { viewModel.clearError() },
                        onActionClick = { actionId -> viewModel.handleAction(actionId) },
                        onNavigate = { nav -> viewModel.handleNavigation(nav) }
                    )
                }
            }
        }
    }
}
```

### Custom Error Actions

```kotlin
// Define custom actions
object CustomActions {
    val ContactSupport = ErrorAction(
        label = UiMessage.Plain("Contact Support"),
        isDestructive = false
    ).apply {
        // actionId is auto-generated from label: "contact_support"
    }

    val ViewDetails = ErrorAction(
        label = UiMessage.Plain("View Details"),
        isDestructive = false
    )
}

// Use in exceptions
throw ActionableException(
    msg = UiMessage.Plain("Payment processing failed"),
    severity = ErrorSeverity.Error,
    presentation = ErrorPresentation.Dialog,
    primaryAction = CustomActions.ContactSupport,
    secondaryAction = ErrorAction.Dismiss
)

// Handle in UI
ErrorPresenter(
    error = error,
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId ->
        when (actionId) {
            "contact_support" -> {
                navController.navigate("support")
                viewModel.clearError()
            }
            "view_details" -> {
                navController.navigate("error-details/${error?.id}")
            }
            "retry" -> viewModel.retry()
            "dismiss" -> viewModel.clearError()
        }
    },
    onNavigate = { nav ->
        // Handle navigation if needed
    }
)
```

### Error with Custom Navigation

```kotlin
// Define custom navigation
class RouteNavigation(val route: String) : ErrorNavigation()

object AppNavigation {
    val Dashboard = RouteNavigation("dashboard")
    val Settings = RouteNavigation("settings")
}

// Use in exceptions
throw AuthException(
    msg = UiMessage.Plain("Your session has expired"),
    severity = ErrorSeverity.Warning,
    presentation = ErrorPresentation.Dialog,
    primaryAction = ErrorAction.Retry,
    navigation = ErrorNavigation.Login  // or AppNavigation.Dashboard
)

// Handle in UI
ErrorPresenter(
    error = error,
    onDismiss = { viewModel.clearError() },
    onActionClick = { actionId -> viewModel.handleAction(actionId) },
    onNavigate = { navigation ->
        when (navigation) {
            ErrorNavigation.Login -> navController.navigate("login")
            ErrorNavigation.Back -> navController.popBackStack()
            ErrorNavigation.Home -> navController.navigate("home")
            is RouteNavigation -> navController.navigate(navigation.route)
            else -> { /* handle other types */ }
        }
    }
)
```

### Theming and Customization

```kotlin
@Composable
fun ThemedErrorExample() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            error = Color(0xFFCF6679),
            surface = Color(0xFF121212),
            onSurface = Color.White
        )
    ) {
        ErrorDialog(
            error = error,
            onDismiss = { viewModel.clearError() },
            onActionClick = { actionId -> viewModel.handleAction(actionId) },
            onNavigate = { nav -> viewModel.handleNavigation(nav) },

            // Override theme colors
            containerColor = MaterialTheme.colorScheme.surface,
            titleStyle = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.error
            ),
            messageStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        )
    }
}
```

## Platform Support

The error-compose module supports all platforms where Compose Multiplatform is available:

- **JVM** (Android, Desktop)
- **JavaScript** (Browser, Node.js)
- **Native** (iOS, macOS) - Can be enabled by uncommenting targets in build.gradle.kts

Note: Currently configured for JVM and JS. Native support can be enabled when needed.

## Component Reference

### ErrorPresenter

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `error` | `ActionableException?` | Yes | The error to display (null if no error) |
| `onDismiss` | `() -> Unit` | Yes | Callback when error is dismissed |
| `onActionClick` | `(String) -> Unit` | Yes | Callback when action button is clicked |
| `onNavigate` | `(ErrorNavigation?) -> Unit` | Yes | Callback for navigation directives |
| `resolver` | `MessageResolver` | No | Message resolver (default: DefaultMessageResolver) |
| `modifier` | `Modifier` | No | Modifier to apply |

### ErrorDialog

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `error` | `ActionableException` | Yes | The error to display |
| `onDismiss` | `() -> Unit` | Yes | Callback when dialog is dismissed |
| `onActionClick` | `(String) -> Unit` | Yes | Callback when action button is clicked |
| `onNavigate` | `(ErrorNavigation?) -> Unit` | Yes | Callback for navigation directives |
| `resolver` | `MessageResolver` | No | Message resolver |
| `modifier` | `Modifier` | No | Modifier to apply |
| `icon` | `@Composable (() -> Unit)?` | No | Custom icon composable |
| `title` | `@Composable (() -> Unit)?` | No | Custom title composable |
| `content` | `@Composable (() -> Unit)?` | No | Custom content composable |
| `actions` | `@Composable (() -> Unit)?` | No | Custom actions composable |
| `titleStyle` | `TextStyle` | No | Text style for title |
| `messageStyle` | `TextStyle` | No | Text style for message |
| `containerColor` | `Color` | No | Background color |

### ErrorSnackbar

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `error` | `ActionableException` | Yes | The error to display |
| `onDismiss` | `() -> Unit` | Yes | Callback when snackbar is dismissed |
| `onActionClick` | `(String) -> Unit` | Yes | Callback when action button is clicked |
| `resolver` | `MessageResolver` | No | Message resolver |
| `snackbarHostState` | `SnackbarHostState` | No | Snackbar host state |
| `modifier` | `Modifier` | No | Modifier to apply |

### ErrorFullScreen

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `error` | `ActionableException` | Yes | The error to display |
| `onActionClick` | `(String) -> Unit` | Yes | Callback when action button is clicked |
| `onNavigate` | `(ErrorNavigation?) -> Unit` | Yes | Callback for navigation directives |
| `resolver` | `MessageResolver` | No | Message resolver |
| `modifier` | `Modifier` | No | Modifier to apply |
| `backgroundColor` | `Color` | No | Background color |
| `messageStyle` | `TextStyle` | No | Text style for message |
| `icon` | `@Composable (() -> Unit)?` | No | Custom icon composable |
| `content` | `@Composable (() -> Unit)?` | No | Custom content composable |
| `actions` | `@Composable (() -> Unit)?` | No | Custom actions composable |

## Best Practices

1. **Use ErrorPresenter**: Let it handle routing to the correct component automatically
2. **State Management**: Store errors in ViewModel StateFlow for reactive UI updates
3. **Clear Errors**: Always clear errors after handling them to prevent showing stale errors
4. **Custom Actions**: Define custom actions for domain-specific error handling
5. **Navigation**: Handle navigation in the UI layer, not in ViewModels
6. **i18n**: Use error-catalog factory functions for automatic i18n support
7. **Severity Levels**: Choose appropriate severity to get the right visual treatment
8. **Error IDs**: Use unique error IDs for analytics and debugging

## Related Documentation

- [error-core README](../error-core/README.md) - Core exception types and metadata
- [error-catalog README](../error-catalog/README.md) - Error messages and i18n
- [Main README](../README.md) - Overall library documentation

## License

Apache License 2.0
