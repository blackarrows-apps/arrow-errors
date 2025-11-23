package io.blackarrows.errors.catalog

/**
 * Provider interface for retrieving error messages from the error catalog.
 *
 * This interface abstracts error message lookup by error code, allowing ViewModels
 * and other components to retrieve user-facing error messages without directly
 * coupling to the error catalog structure.
 *
 * The ErrorProvider searches through all available error catalogs (Network, Storage,
 * Auth, Session, etc.) to find an error matching the provided code.
 *
 * Usage:
 * ```kotlin
 * // In ViewModel
 * class SessionViewModel(
 *     private val errorProvider: ErrorProvider,
 *     private val getSessionUseCase: GetSessionUseCase
 * ) : ViewModel() {
 *
 *     fun loadSession(userId: String) {
 *         viewModelScope.launch {
 *             getSessionUseCase(userId).collect { resource ->
 *                 when (resource.status) {
 *                     ERROR -> {
 *                         // Get user-facing message from error catalog
 *                         val message = errorProvider.getErrorMessage(resource.error.errorCode)
 *                         _state.update { it.copy(errorMessage = message) }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * @see DefaultErrorProvider for the standard implementation
 */
interface ErrorProvider {
    /**
     * Retrieves a user-facing error message for the given error code.
     *
     * This method searches all registered error catalogs (Network, Storage, Auth,
     * Session, etc.) to find an error with a matching code.
     *
     * @param errorCode The 7-digit error code (format: LCCCSSS)
     * @return The user-facing error message, or a generic fallback message if the
     *         error code is not found in any catalog
     *
     * Example:
     * ```kotlin
     * val message = errorProvider.getErrorMessage(1001000)
     * // Returns: "Network is unavailable. Please check your connection."
     *
     * val message = errorProvider.getErrorMessage(2200050)
     * // Returns: "Invalid email or password. Please try again."
     *
     * val message = errorProvider.getErrorMessage(9999999)
     * // Returns: "An unexpected error occurred. Please try again." (fallback)
     * ```
     */
    fun getErrorMessage(errorCode: Int): String

    /**
     * Retrieves the full ErrorCatalog entry for the given error code.
     *
     * This method returns the complete catalog entry, which includes both the
     * error code and message. Useful when you need access to the full error object.
     *
     * @param errorCode The 7-digit error code (format: LCCCSSS)
     * @return The ErrorCatalog entry if found, null otherwise
     *
     * Example:
     * ```kotlin
     * val error = errorProvider.getError(1001000)
     * if (error != null) {
     *     println("Code: ${error.errorCode}, Message: ${error.message}")
     * }
     * ```
     */
    fun getError(errorCode: Int): ErrorCatalog?
}
