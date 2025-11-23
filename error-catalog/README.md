# :shared:core:error-catalog

Single source of truth for error messages across all platforms (Android & iOS).

## Overview

The error catalog is a **simple, lightweight KMP module** that provides **consistent error messages** across Android and iOS. It contains only error codes (Int) and user-facing messages (String) - nothing more.

This catalog is **published to Maven** so that iOS and Android display identical error messages to users.

## Design Philosophy

**Simplicity First:**
- ✅ Only `errorCode: Int` and `message: String`
- ✅ Direct property access (O(1), zero allocations)
- ✅ Compile-time constants via `data object`
- ✅ No lookup registry needed
- ✅ No UI concerns (actions, navigation, display types)
- ✅ No runtime dependencies

**Efficiency:**
```kotlin
val error = NetworkErrorCatalog.Unavailable
val code: Int = error.errorCode      // Direct field access
val msg: String = error.message       // Direct field access
// No lookups, no allocations, no overhead
```

## Module Structure

```
error-catalog/
├── ErrorCatalog.kt              # Base sealed interface
├── NetworkErrorCatalog.kt       # Network errors (1-001-XXX)
├── StorageErrorCatalog.kt       # Storage errors (1-010-XXX)
├── AuthErrorCatalog.kt          # Auth errors (1-020-XXX)
├── SessionErrorCatalog.kt       # Session errors (2-200-XXX)
└── README.md                    # This file
```

## Error Code Format

Error codes follow a **7-digit format: `L-CCC-SSS`**

```
[L] [CCC] [SSS]
 │    │     └─── Specific error within context (000-999)
 │    └───────── Context/Feature code (001-999)
 └────────────── Layer code (1 digit)
```

### Layer Codes

| Code | Layer | Description |
|------|-------|-------------|
| `1` | Infrastructure | Cross-cutting concerns (network, storage, auth) |
| `2` | Domain | Business logic errors (session, safety, finances) |
| `3` | Presentation | UI-specific errors (Android/iOS) |
| `4` | Data | Data layer errors |

### Infrastructure Context Codes (Layer 1)

| Code Range | Context | Description |
|------------|---------|-------------|
| `001` | Network | Network connectivity, timeouts, DNS |
| `010` | Storage | Local storage, database, file I/O |
| `020` | Auth | Authentication, authorization |

### Domain Context Codes (Layer 2)

| Code Range | Context | Description |
|------------|---------|-------------|
| `200` | Session | Session management, login |
| `210` | ROST | ROST-specific operations |
| `220` | Safety | Safety inspections/observations |
| `230` | Sync | Data synchronization |
| `240` | Finances | Financial operations |

### Examples

| Error Code | Format | Meaning |
|------------|--------|---------|
| `1001000` | 1-001-000 | Infrastructure → Network → Unavailable |
| `1001500` | 1-001-500 | Infrastructure → Network → Timeout |
| `1010000` | 1-010-000 | Infrastructure → Storage → Unavailable |
| `1020000` | 1-020-000 | Infrastructure → Auth → Unauthorized |
| `2200000` | 2-200-000 | Domain → Session → Fetch Network Error |
| `2200050` | 2-200-050 | Domain → Session → Login Invalid Credentials |

## Base Interface

```kotlin
sealed interface ErrorCatalog {
    /**
     * 7-digit error code: LCCCSSS
     */
    val errorCode: Int

    /**
     * User-facing error message (displayed in UI).
     * Should be clear, concise, and actionable.
     */
    val message: String
}
```

## Available Catalogs

### 1. NetworkErrorCatalog

**Context Code:** `001`
**Error Code Range:** `1-001-000` to `1-001-999`

Network-related infrastructure errors.

```kotlin
sealed interface NetworkErrorCatalog : ErrorCatalog {
    data object Unavailable          // 1001000 - No connection
    data object Timeout              // 1001500 - Request timeout
    data object DnsFailure           // 1001001 - DNS resolution failed
    data object SslError             // 1001002 - SSL/TLS error
    data object ConnectionRefused    // 1001003 - Server refused connection
    data object Unknown              // 1001999 - Unclassified network error
}
```

### 2. StorageErrorCatalog

**Context Code:** `010`
**Error Code Range:** `1-010-000` to `1-010-999`

Local storage operation errors (database, file system, cache).

```kotlin
sealed interface StorageErrorCatalog : ErrorCatalog {
    data object Unavailable          // 1010000 - Storage unavailable
    data object InsufficientSpace    // 1010001 - Disk full
    data object DatabaseCorrupted    // 1010002 - Database corruption
    data object ReadFailed           // 1010100 - Read operation failed
    data object WriteFailed          // 1010200 - Write operation failed
    data object DeleteFailed         // 1010300 - Delete operation failed
    data object Unknown              // 1010999 - Unclassified storage error
}
```

### 3. AuthErrorCatalog

**Context Code:** `020`
**Error Code Range:** `1-020-000` to `1-020-999`

Authentication and authorization errors.

```kotlin
sealed interface AuthErrorCatalog : ErrorCatalog {
    data object Unauthorized         // 1020000 - 401 Unauthorized
    data object TokenExpired         // 1020001 - JWT expired
    data object Forbidden            // 1020002 - 403 Forbidden
    data object RefreshTokenInvalid  // 1020003 - Refresh token invalid
    data object Unknown              // 1020999 - Unclassified auth error
}
```

### 4. SessionErrorCatalog

**Context Code:** `200`
**Error Code Range:** `2-200-000` to `2-200-999`

Session domain errors (fetch, save, authentication).

```kotlin
sealed interface SessionErrorCatalog : ErrorCatalog {
    // Fetch Operations (2-200-000 to 2-200-099)
    data object FetchNetworkUnavailable  // 2200000
    data object FetchStorageError        // 2200001
    data object FetchNotFound            // 2200002
    data object FetchInvalidData         // 2200003
    data object FetchTimeout             // 2200500
    data object FetchUnknown             // 2200999

    // Save Operations (2-200-100 to 2-200-199)
    data object SaveStorageError         // 2200100
    data object SaveValidationFailed     // 2200400
    data object SaveUnknown              // 2200199

    // Authentication (2-200-050 to 2-200-059)
    data object LoginInvalidCredentials  // 2200050
    data object LoginAccountLocked       // 2200051
    data object LoginEmailNotVerified    // 2200052
    data object LoginServerError         // 2200053
    data object LoginNetworkError        // 2200054
}
```

## Usage

### Basic Usage

```kotlin
// Access error directly
val error = NetworkErrorCatalog.Unavailable

// Get properties
val code: Int = error.errorCode        // 1001000
val message: String = error.message    // "Network is unavailable. Please check your connection."

// Use in logging
logger.error("Error ${error.errorCode}: ${error.message}")
```

### In DataSources/Repositories

```kotlin
// Map DataLayerException to ErrorCatalog for logging/analytics
fun DataLayerException.toErrorCatalog(): ErrorCatalog? {
    return when (this) {
        is DataLayerException.NetworkError -> NetworkErrorCatalog.Unavailable
        is DataLayerException.StorageError -> StorageErrorCatalog.Unavailable
        is DataLayerException.AuthError -> AuthErrorCatalog.Unauthorized
        // ... other mappings
        else -> null
    }
}
```

### In UseCases

```kotlin
// Use catalog for consistent error codes in DomainError DTOs
class GetSessionUseCase(...) {
    override fun invoke(userId: String) = flow {
        try {
            val session = repository.getSession(userId)
            emit(Resource.success(session))
        } catch (e: SessionDomainException.FetchFailed) {
            logger.exception("GetSessionUseCase", e)

            // Use catalog for error code + message
            val catalogError = when (e.reason) {
                NETWORK_UNAVAILABLE -> SessionErrorCatalog.FetchNetworkUnavailable
                STORAGE_ERROR -> SessionErrorCatalog.FetchStorageError
                NOT_FOUND -> SessionErrorCatalog.FetchNotFound
                else -> SessionErrorCatalog.FetchUnknown
            }

            emit(Resource.error(
                SessionDomainError.FetchFailed(
                    errorCode = catalogError.errorCode,
                    message = catalogError.message,
                    reason = e.reason.name
                )
            ))
        }
    }
}
```

### For Analytics

```kotlin
// Log error codes for analytics
fun trackError(error: ErrorCatalog) {
    analytics.logEvent("error_occurred") {
        param("error_code", error.errorCode)
        param("error_message", error.message)
    }
}
```

## Adding New Error Catalogs

To add a new catalog (e.g., for ROST, Safety Inspection, Map, etc.):

### 1. Create new catalog file with companion registry

```kotlin
// RostErrorCatalog.kt
package com.segsolutions.mobile.errors.catalog

import kotlinx.serialization.Serializable

/**
 * ROST domain errors.
 * Context Code: 210
 * Error Code Range: 2-210-000 to 2-210-999
 */
sealed interface RostErrorCatalog : ErrorCatalog {

    /**
     * Failed to fetch ROST data.
     * Error Code: 2-210-000
     */
    @Serializable
    data object FetchFailed : RostErrorCatalog {
        override val errorCode: Int = 2210000
        override val message: String = "Failed to fetch ROST data. Please try again."
    }

    // Add more errors...

    companion object {
        /**
         * Returns a map of all ROST error catalog entries indexed by error code.
         * Used by ErrorProvider for O(1) lookup performance.
         */
        fun registry(): Map<Int, ErrorCatalog> = mapOf(
            FetchFailed.errorCode to FetchFailed,
            // Add more error mappings here
        )
    }
}
```

**Important:** The `companion object registry()` function keeps error mappings organized within the catalog itself, preventing `DefaultErrorProvider` from becoming a god class.

### 2. Register in DefaultErrorProvider

Add a single line to `DefaultErrorProvider`:

```kotlin
// In DefaultErrorProvider.kt
private fun buildErrorRegistry(): Map<Int, ErrorCatalog> {
    return buildMap {
        putAll(NetworkErrorCatalog.registry())
        putAll(StorageErrorCatalog.registry())
        putAll(AuthErrorCatalog.registry())
        putAll(SessionErrorCatalog.registry())
        putAll(RostErrorCatalog.registry())  // ← Add this line
    }
}
```

### 3. Choose appropriate error code range

Refer to the [Error Code Format](#error-code-format) section and choose:
- **Layer code** (1=Infra, 2=Domain, 3=Presentation, 4=Data)
- **Context code** (unique 3-digit code for your feature)
- **Specific codes** (000-999 for individual errors)

### 4. Keep it simple

Remember:
- ✅ Only `errorCode` and `message`
- ✅ Use `data object` for zero allocations
- ✅ Clear, actionable messages
- ✅ Consistent naming convention
- ✅ Add `companion object registry()` for ErrorProvider lookup

## What This Module Does NOT Contain

This catalog intentionally excludes:

- ❌ **Actions** (RETRY, DISMISS, etc.) - These belong in PresentationError (Android)
- ❌ **Navigation** (go to login, go home) - These belong in PresentationError (Android)
- ❌ **Display types** (dialog, snackbar) - These belong in PresentationError (Android)
- ❌ **Technical descriptions** - Use logging/monitoring for technical details
- ❌ **Lookup registry** - Direct access is more efficient
- ❌ **Helper functions** - Keep it minimal
- ❌ **Retriable property** - This is a domain concern (see `DataLayerException.retriable`)

## Relationship to Other Modules

```
┌─────────────────────────────────────────────────────────────┐
│ :shared:core:error-catalog (THIS MODULE)                   │
│ - Simple error code + message pairs                         │
│ - KMP, published to Maven                                   │
│ - Used for: logging, analytics, consistent messages         │
└────────────────────┬────────────────────────────────────────┘
                     │ referenced by
                     ▼
┌─────────────────────────────────────────────────────────────┐
│ :shared:core:errors                                         │
│ - Rich KMP exceptions (DataLayerException, DomainException) │
│ - Can wrap causes, contain complex types                    │
│ - NOT directly tied to catalog                              │
└────────────────────┬────────────────────────────────────────┘
                     │ mapped to
                     ▼
┌─────────────────────────────────────────────────────────────┐
│ DomainError DTOs (primitives-only)                         │
│ - Uses error catalog codes and messages                     │
│ - Serializable, crosses UseCase → ViewModel boundary        │
└────────────────────┬────────────────────────────────────────┘
                     │ mapped to (Android only)
                     ▼
┌─────────────────────────────────────────────────────────────┐
│ PresentationError (Android)                                 │
│ - UI-ready errors with actions, navigation, display types   │
│ - Uses catalog messages for consistency                     │
└─────────────────────────────────────────────────────────────┘
```

## Best Practices

### ✅ DO:

1. **Keep messages user-friendly**
   ```kotlin
   // ✅ Good
   override val message: String = "Network is unavailable. Please check your connection."

   // ❌ Bad
   override val message: String = "IOException: java.net.UnknownHostException"
   ```

2. **Use catalog for consistent messaging**
   ```kotlin
   // ✅ Use catalog error
   val error = NetworkErrorCatalog.Unavailable
   emit(Resource.error(
       NetworkDomainError(
           errorCode = error.errorCode,
           message = error.message  // Same message everywhere
       )
   ))
   ```

3. **Add new catalogs for each domain**
   ```kotlin
   // ✅ Create domain-specific catalogs
   sealed interface RostErrorCatalog : ErrorCatalog { ... }
   sealed interface SafetyErrorCatalog : ErrorCatalog { ... }
   sealed interface MapErrorCatalog : ErrorCatalog { ... }
   ```

### ❌ DON'T:

1. **Don't add UI concerns**
   ```kotlin
   // ❌ DON'T
   data object Unavailable : NetworkErrorCatalog {
       override val errorCode: Int = 1001000
       override val message: String = "Network unavailable"
       val action: ErrorAction = ErrorAction.RETRY  // ❌ UI concern
   }
   ```

2. **Don't add lookup logic**
   ```kotlin
   // ❌ DON'T - No need for registry
   object ErrorCatalogLookup {
       fun findByCode(code: Int): ErrorCatalog? = ...
   }

   // ✅ DO - Direct access
   val error = NetworkErrorCatalog.Unavailable
   ```

3. **Don't make messages too technical**
   ```kotlin
   // ❌ DON'T
   override val message: String = "SSLHandshakeException: PKIX path building failed"

   // ✅ DO
   override val message: String = "Secure connection failed. Please try again later."
   ```

## Publishing to Maven

This module is configured to publish to Maven:

```kotlin
// build.gradle.kts
publishing {
    publications {
        withType<MavenPublication> {
            groupId = "com.segsolutions.mobile"
            artifactId = "error-catalog-$name"
            version = "1.0.0"
        }
    }
}
```

### Publish to Maven Local

```bash
./gradlew :shared:core:error-catalog:publishToMavenLocal
```

### Publish to Remote Repository

Configure remote repository in `build.gradle.kts` and run:

```bash
./gradlew :shared:core:error-catalog:publish
```

## Related Documentation

- **Error Handling Architecture:** `wiki/kmp/errors/Error Handling Architecture.md`
- **KMP Errors Module:** `shared/core/errors/README.md`
- **Android Presentation Errors:** `core/errors/README.md` (if exists)

## Dependencies

```kotlin
dependencies {
    api(libs.kotlinx.serialization)  // For @Serializable
}
```

## Summary

This module provides a **simple, efficient catalog** of error codes and messages for consistent error handling across Android and iOS. It's designed for:
- Direct property access (no lookups)
- Zero-allocation singletons
- Platform-agnostic messaging
- Easy extension with new domains

Keep it simple: just error codes and messages, nothing more.
