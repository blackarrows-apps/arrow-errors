package io.blackarrows.errors.catalog.di

import io.blackarrows.errors.catalog.DefaultErrorProvider
import io.blackarrows.errors.catalog.ErrorProvider
import org.koin.dsl.module

/**
 * Koin DI module for error catalog components.
 *
 * Provides a singleton instance of ErrorProvider for looking up error messages
 * by error code across the application.
 *
 * Usage:
 * ```kotlin
 * // In your Koin setup
 * startKoin {
 *     modules(
 *         errorCatalogModule,
 *         // ... other modules
 *     )
 * }
 *
 * // In ViewModels or other classes
 * class SessionViewModel(
 *     private val errorProvider: ErrorProvider  // Injected by Koin
 * ) : ViewModel() {
 *     // Use errorProvider to get error messages
 * }
 * ```
 */
val errorCatalogModule =
    module {
        single<ErrorProvider> { DefaultErrorProvider() }
    }
