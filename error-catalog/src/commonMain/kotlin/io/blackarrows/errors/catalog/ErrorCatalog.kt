package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Error Catalog - Single source of truth for all error messages across platforms.
 *
 * This catalog is published to Maven so iOS and Android display identical error messages.
 *
 * Error Code Format: CC-SSS (5 digits)
 * - CC (2 digits): Category (10-99)
 *   - 10 = Network errors
 *   - 11 = Storage errors
 *   - 12 = Auth errors
 *   - 20 = Session errors
 *   - etc.
 * - SSS (3 digits): Specific error within category (000-999)
 *   - 000-099 = Fetch/Read operations
 *   - 100-199 = Save/Write operations
 *   - 200-299 = Delete operations
 *   - 400-499 = Validation errors
 *   - 500-599 = Timeout errors
 *   - 999 = Unknown/fallback
 *
 * Usage:
 * ```kotlin
 * // Android
 * val error = NetworkErrorCatalog.Unavailable
 * println(error.message) // "Network is unavailable. Please check your connection."
 * println(error.errorCode) // 10000
 *
 * // iOS
 * let error = NetworkErrorCatalog.Unavailable
 * print(error.message) // Same message
 * print(error.errorCode) // Same code
 * ```
 */
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
