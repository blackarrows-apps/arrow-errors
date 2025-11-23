package io.blackarrows.errors.catalog

/**
 * Default implementation of ErrorProvider that searches through all error catalogs.
 *
 * This implementation maintains a registry of all available error catalog entries
 * and performs lookups by error code. It provides O(1) lookup performance using
 * a map-based registry.
 *
 * The registry is built by collecting entries from each individual catalog's
 * registry function. This keeps catalog entries organized and prevents the
 * provider from becoming a god class.
 *
 * This implementation is thread-safe and can be used as a singleton.
 *
 * @see ErrorProvider
 */
class DefaultErrorProvider : ErrorProvider {

    /**
     * Registry of all error catalog entries, indexed by error code.
     * Built once during initialization for O(1) lookup performance.
     */
    private val errorRegistry: Map<Int, ErrorCatalog> = buildErrorRegistry()

    /**
     * Fallback message for unknown error codes.
     */
    private val fallbackMessage = "An unexpected error occurred. Please try again."

    override fun getErrorMessage(errorCode: Int): String {
        return errorRegistry[errorCode]?.message ?: fallbackMessage
    }

    override fun getError(errorCode: Int): ErrorCatalog? {
        return errorRegistry[errorCode]
    }

    /**
     * Builds the error registry by merging registries from all catalogs.
     *
     * This method collects error registries from:
     * - NetworkErrorCatalog (1-001-XXX)
     * - StorageErrorCatalog (1-010-XXX)
     * - AuthErrorCatalog (1-020-XXX)
     * - SessionErrorCatalog (2-200-XXX)
     *
     * To add a new catalog:
     * 1. Add its companion object registry function to the list below
     * 2. The catalog's entries will automatically be included
     *
     * This approach keeps each catalog self-contained and prevents this
     * method from becoming a maintenance bottleneck.
     */
    private fun buildErrorRegistry(): Map<Int, ErrorCatalog> {
        return buildMap {
            putAll(NetworkErrorCatalog.registry())
            putAll(StorageErrorCatalog.registry())
            putAll(AuthErrorCatalog.registry())

            putAll(SessionErrorCatalog.registry())
        }
    }
}
