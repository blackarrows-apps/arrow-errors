package io.blackarrows.errors.catalog

import kotlinx.serialization.Serializable

/**
 * Error Catalog - Single source of truth for all error messages across platforms.
 *
 * This catalog is published to Maven so iOS and Android display identical error messages.
 *
 * Error Code Format: L-CCC-SSS (7 digits)
 * - L (1 digit): Layer (1=Infra, 2=Domain, 3=Presentation, 4=Data)
 * - CCC (3 digits): Context/Feature (001-999)
 * - SSS (3 digits): Specific error (000-999)
 *
 * Usage:
 * ```kotlin
 * // Android
 * val error = NetworkErrorCatalog.Unavailable
 * println(error.message) // "Network is unavailable. Please check your connection."
 * println(error.errorCode) // 1001000
 *
 * // iOS
 * let error = NetworkErrorCatalog.Unavailable
 * print(error.message) // Same message
 * print(error.errorCode) // Same code
 * ```
 */
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
