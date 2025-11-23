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

Error codes follow a **5-digit format: `CC-SSS`**

```
[CC] [SSS]
 │     └─── Specific error within category (000-999)
 └───────── Category code (10-99)
```

### Category Codes

| Code | Category | Description |
|------|----------|-------------|
| `10` | Network | Network connectivity, timeouts, DNS, SSL |
| `11` | Storage | Local storage, database, file I/O |
| `12` | Auth | Authentication, authorization, tokens |
| `20` | Session | Session management, login operations |
| `30+` | Custom | Available for library consumers to define |

### Specific Error Ranges (SSS)

| Range | Purpose | Examples |
|-------|---------|----------|
| `000-099` | Fetch/Read operations | Unavailable, Not Found, Invalid Data |
| `100-199` | Save/Write operations | Write Failed, Save Error |
| `200-299` | Delete operations | Delete Failed |
| `300-399` | Update operations | Update Failed |
| `400-499` | Validation errors | Validation Failed, Invalid Input |
| `500-599` | Timeout errors | Request Timeout, Connection Timeout |
| `999` | Unknown/fallback | Generic error for category |

### Examples

| Error Code | Format | Meaning |
|------------|--------|---------|
| `10000` | 10-000 | Network → Unavailable |
| `10500` | 10-500 | Network → Timeout |
| `11000` | 11-000 | Storage → Unavailable |
| `11100` | 11-100 | Storage → Read Failed |
| `12000` | 12-000 | Auth → Unauthorized |
| `20000` | 20-000 | Session → Fetch Network Error |
| `20050` | 20-050 | Session → Login Invalid Credentials |

## Base Interface

```kotlin
sealed interface ErrorCatalog {
    /**
     * 5-digit error code: CCSSS
     * Format: CC (category 10-99) + SSS (specific error 000-999)
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

**Category Code:** `10`
**Error Code Range:** `10-000` to `10-999`

Network-related errors.

```kotlin
sealed interface NetworkErrorCatalog : ErrorCatalog {
    data object Unavailable          // 10000 - No connection
    data object DnsFailure           // 10001 - DNS resolution failed
    data object SslError             // 10002 - SSL/TLS error
    data object ConnectionRefused    // 10003 - Server refused connection
    data object Timeout              // 10500 - Request timeout
    data object Unknown              // 10999 - Unclassified network error
}
```

### 2. StorageErrorCatalog

**Category Code:** `11`
**Error Code Range:** `11-000` to `11-999`

Local storage operation errors (database, file system, cache).

```kotlin
sealed interface StorageErrorCatalog : ErrorCatalog {
    data object Unavailable          // 11000 - Storage unavailable
    data object InsufficientSpace    // 11001 - Disk full
    data object DatabaseCorrupted    // 11002 - Database corruption
    data object ReadFailed           // 11100 - Read operation failed
    data object WriteFailed          // 11200 - Write operation failed
    data object DeleteFailed         // 11300 - Delete operation failed
    data object Unknown              // 11999 - Unclassified storage error
}
```

### 3. AuthErrorCatalog

**Category Code:** `12`
**Error Code Range:** `12-000` to `12-999`

Authentication and authorization errors.

```kotlin
sealed interface AuthErrorCatalog : ErrorCatalog {
    data object Unauthorized         // 12000 - 401 Unauthorized
    data object TokenExpired         // 12001 - JWT expired
    data object Forbidden            // 12002 - 403 Forbidden
    data object RefreshTokenInvalid  // 12003 - Refresh token invalid
    data object Unknown              // 12999 - Unclassified auth error
}
```

### 4. SessionErrorCatalog

**Category Code:** `20`
**Error Code Range:** `20-000` to `20-999`

Session domain errors (fetch, save, authentication).

```kotlin
sealed interface SessionErrorCatalog : ErrorCatalog {
    // Fetch Operations (20-000 to 20-099)
    data object FetchNetworkUnavailable  // 20000
    data object FetchStorageError        // 20001
    data object FetchNotFound            // 20002
    data object FetchInvalidData         // 20003
    data object LoginInvalidCredentials  // 20050
    data object LoginAccountLocked       // 20051
    data object LoginEmailNotVerified    // 20052
    data object LoginServerError         // 20053
    data object LoginNetworkError        // 20054

    // Save Operations (20-100 to 20-199)
    data object SaveStorageError         // 20100
    data object SaveUnknown              // 20199

    // Validation & Timeouts (20-400 to 20-599)
    data object SaveValidationFailed     // 20400
    data object FetchTimeout             // 20500
    data object FetchUnknown             // 20999
}
```

## Usage

### Basic Usage

```kotlin
// Access error directly
val error = NetworkErrorCatalog.Unavailable

// Get properties
val code: Int = error.errorCode        // 10000
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
 * Payment domain errors.
 * Category Code: 30
 * Error Code Range: 30-000 to 30-999
 */
sealed interface PaymentErrorCatalog : ErrorCatalog {

    /**
     * Failed to process payment.
     * Error Code: 30-000
     */
    @Serializable
    data object ProcessFailed : PaymentErrorCatalog {
        override val errorCode: Int = 30000
        override val message: String = "Failed to process payment. Please try again."
    }

    // Add more errors...

    companion object {
        /**
         * Returns a map of all payment error catalog entries indexed by error code.
         * Used by ErrorProvider for O(1) lookup performance.
         */
        fun registry(): Map<Int, ErrorCatalog> = mapOf(
            ProcessFailed.errorCode to ProcessFailed,
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
        putAll(PaymentErrorCatalog.registry())  // ← Add this line
    }
}
```

### 3. Choose appropriate error code range

Refer to the [Error Code Format](#error-code-format) section and choose:
- **Category code** (10-99, avoid 10-20 which are reserved for core categories)
- **Specific codes** (000-999 for individual errors within your category)

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
   sealed interface PaymentErrorCatalog : ErrorCatalog { ... }  // Category 30
   sealed interface OrderErrorCatalog : ErrorCatalog { ... }     // Category 31
   sealed interface ShippingErrorCatalog : ErrorCatalog { ... }  // Category 32
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
