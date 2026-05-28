# Changelog

All notable changes to Arrow Errors will be documented in this file.

## [1.1.2] - 2026-05-28

### Changed

#### error-catalog

- **Added `wasmJs` target** — `arrow-errors-catalog` now publishes a Kotlin/Wasm artifact,
  enabling use in Compose Multiplatform web (wasmJs) projects. No API changes.

#### error-compose

- **Added `wasmJs` target** — `arrow-errors-compose` now publishes a Kotlin/Wasm artifact,
  enabling use in Compose Multiplatform web (wasmJs) projects. No API changes.

---

## [1.1.1] - 2026-05-26

### Changed

#### error-core

- **Added `wasmJs` target** — `arrow-errors-core` now publishes a Kotlin/Wasm artifact,
  enabling use in Compose Multiplatform web (wasmJs) projects alongside the existing
  JVM, JS, iOS, macOS, Linux, and Windows targets. No API changes.

---

## [1.1.0] - 2025-02-01

### Added

#### error-core

- **`toActionableException()` extension** - Simple utility for converting exceptions in catch blocks
  - Rethrows `CancellationException` to preserve coroutine cancellation
  - Passes through existing `ActionableException` unchanged
  - Supports custom mapping with fallback to `UnknownException`
  ```kotlin
  catch (e: Exception) {
      _error.value = e.toActionableException { throwable ->
          when (throwable) {
              is SocketTimeoutException -> NetworkException(error = throwable)
              else -> null // Falls back to UnknownException
          }
      }
  }
  ```

- **`CommonActionIds` object** - Type-safe constants for action IDs
  - `RETRY`, `DISMISS`, `CANCEL`, `CLOSE`, `OK`, `CONFIRM`, `DELETE`, `REMOVE`
  - Additional: `GO_BACK`, `LOGIN`, `REFRESH`, `CONTACT_SUPPORT`
  ```kotlin
  when (actionId) {
      CommonActionIds.RETRY -> viewModel.retry()
      CommonActionIds.DISMISS -> viewModel.clearError()
  }
  ```

- **`CommonRoutes` object + `suggestedRoute()` extension** - Route constants with automatic mapping
  - Constants: `LOGIN`, `HOME`, `SETTINGS`, `SIGNUP`, `PROFILE`, `HELP`, `SUPPORT`, `STORE`
  - Extension maps `ErrorNavigation` to route strings
  ```kotlin
  navigation.suggestedRoute()?.let { route ->
      navController.navigate(route)
  }
  ```

#### error-compose

- **ErrorTheme configuration system** - Global theme customization
  - `ErrorThemeProvider` composable for wrapping app content
  - `ErrorColors` - Custom colors for severity levels (info, warning, error, critical, surface, onSurface)
  - `ErrorSpacing` - Configurable sizes (dialogIconSize, fullScreenIconSize, contentPadding, itemSpacing)
  - `ErrorTypography` - Custom text styles (titleStyle, messageStyle)
  - All values support `Unspecified`/`null` to fall back to Material 3 defaults
  ```kotlin
  ErrorThemeProvider(
      theme = ErrorTheme(
          colors = ErrorColors(warning = Color(0xFFFF9800)),
          spacing = ErrorSpacing(dialogIconSize = 40.dp)
      )
  ) {
      // App content
  }
  ```

- **Snackbar hoisting** - Delegate snackbar display to parent Scaffold
  - `SnackbarRequest` data class with message, actionLabel, duration, callbacks
  - `onSnackbar` parameter in `ErrorPresenter`
  ```kotlin
  ErrorPresenter(
      error = error,
      onSnackbar = { request ->
          scope.launch {
              snackbarHostState.showSnackbar(
                  message = request.message,
                  actionLabel = request.actionLabel,
                  duration = request.duration
              )
          }
      }
  )
  ```

### Changed

- `ErrorDialog` parameters `titleStyle` and `messageStyle` are now nullable (null = use theme/Material defaults)
- `ErrorDialog` and `ErrorFullScreen` `containerColor`/`backgroundColor` now default to `Color.Unspecified` for theme fallback
- Components now read from `LocalErrorTheme` for consistent styling

---

## [1.0.1] - Previous Release

Initial stable release with:
- ActionableException base class
- ErrorAction and ErrorNavigation (now extendable)
- ErrorPresenter, ErrorDialog, ErrorSnackbar, ErrorFullScreen components
- i18n support with English, Spanish, French translations
- Error catalog with factory functions
