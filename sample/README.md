# Arrow Errors Sample

Interactive playground Android app for demonstrating the Arrow Errors library.

## Overview

This sample application provides a comprehensive playground for exploring and testing all features of the Arrow Errors library, including:

- **Network Errors** - Various network-related error scenarios
- **Authentication Errors** - Auth and session management errors
- **Storage Errors** - Disk and storage-related errors
- **Session Errors** - Session validation failures
- **Severity Levels** - Info, Warning, Error, and Critical severity demonstrations
- **Custom Errors** - Custom configurations with navigation and multiple actions
- **Internationalization** - Switch between English, Spanish, and French

## Features

### Error Presentations
- **Snackbar** - Brief notifications for non-critical errors
- **Dialog** - Modal dialogs for important errors
- **Full Screen** - Full-screen error states for critical errors
- **Silent** - Logged errors that don't interrupt the user

### Interactive Elements
- **Locale Selector** - Test i18n by switching between languages
- **Status Display** - See which actions and navigations were triggered
- **Organized Sections** - Errors grouped by type for easy exploration

## Running the Sample

### Build and Install on Device/Emulator

```bash
./gradlew :sample:installDebug
```

### Build APK

```bash
./gradlew :sample:assembleDebug
```

The APK will be in `sample/build/outputs/apk/debug/`

### Run from Android Studio

1. Open the project in Android Studio
2. Select the `sample` configuration
3. Click Run (or Shift+F10)

## Requirements

- **Android SDK**: API 21+ (Android 5.0 Lollipop)
- **Target SDK**: API 35 (Android 15)
- **Kotlin**: 2.1.0
- **Compose**: Material 3

## Project Structure

```
sample/
├── src/main/
│   ├── kotlin/io/blackarrows/errors/sample/
│   │   ├── MainActivity.kt                # Main activity
│   │   ├── ErrorPlaygroundScreen.kt       # UI with all error scenarios
│   │   └── ErrorPlaygroundViewModel.kt    # ViewModel with error triggers
│   ├── res/
│   │   └── values/
│   │       └── strings.xml                # App name and resources
│   └── AndroidManifest.xml
├── build.gradle.kts
└── README.md
```

## Usage as Reference

This sample demonstrates best practices for:

1. **Setting up error handling** in a Compose Android app
2. **Using ErrorPresenter** to automatically display errors
3. **Handling actions and navigation** from errors
4. **Configuring i18n** with DefaultMessageResolver
5. **Creating custom errors** with specific actions and navigation
6. **Using factory functions** from error-catalog for common error types

## Code Examples

### Basic Error Handling

```kotlin
class MyViewModel : ViewModel() {
    private val _error = MutableStateFlow<ActionableException?>(null)
    val error = _error.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            try {
                repository.fetchData()
            } catch (e: IOException) {
                _error.value = networkException(error = e)
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
```

### Displaying Errors in UI

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val error by viewModel.error.collectAsState()

    Scaffold { padding ->
        MyContent(modifier = Modifier.padding(padding))

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
                // Handle navigation
            }
        )
    }
}
```

## Customization

The sample app can be easily extended to:

- Add new error scenarios
- Test custom error types
- Experiment with different UI configurations
- Test integration with your own i18n system

## License

This sample application is part of the Arrow Errors library and is licensed under the Apache License 2.0.
