package io.blackarrows.errors.extensions

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.network.UnknownException
import kotlin.coroutines.cancellation.CancellationException

/**
 * Converts a [Throwable] to an [ActionableException] for consistent error handling.
 *
 * This extension provides a convenient way to ensure any exception can be displayed
 * to users through the error presentation system. It differs from [mapError] in that:
 *
 * - [mapError]: Structured mapping with `isNetwork` flag, returns [Throwable], designed
 *   for error translation pipelines
 * - [toActionableException]: Simple utility for catch blocks, always returns [ActionableException],
 *   designed for quick conversion at the point of error handling
 *
 * ## Behavior
 *
 * 1. **CancellationException**: Re-thrown to preserve coroutine cancellation semantics
 * 2. **ActionableException**: Passed through unchanged (no double-wrapping)
 * 3. **Custom mapping**: If provided, the mapper is applied first
 * 4. **Fallback**: If no mapping applies, wraps in [UnknownException]
 *
 * ## Basic Usage
 *
 * ```kotlin
 * suspend fun loadData(): Result<Data> = runCatching {
 *     api.fetchData()
 * }.getOrElse { error ->
 *     throw error.toActionableException()
 * }
 * ```
 *
 * ## With Custom Mapping
 *
 * ```kotlin
 * suspend fun loadData(): Result<Data> = runCatching {
 *     api.fetchData()
 * }.getOrElse { error ->
 *     throw error.toActionableException { throwable ->
 *         when (throwable) {
 *             is HttpException -> when (throwable.code) {
 *                 401 -> AuthException()
 *                 404 -> NotFoundException()
 *                 else -> null  // Fall through to UnknownException
 *             }
 *             is SocketTimeoutException -> NetworkException(
 *                 msg = UiMessage.Plain("Connection timed out")
 *             )
 *             else -> null  // Fall through to UnknownException
 *         }
 *     }
 * }
 * ```
 *
 * ## In ViewModel
 *
 * ```kotlin
 * class MyViewModel : ViewModel() {
 *     private val _error = MutableStateFlow<ActionableException?>(null)
 *     val error: StateFlow<ActionableException?> = _error
 *
 *     fun loadData() {
 *         viewModelScope.launch {
 *             try {
 *                 repository.fetchData()
 *             } catch (e: Exception) {
 *                 _error.value = e.toActionableException()
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * @param mapper Optional function to map specific exceptions to [ActionableException].
 *               Return `null` to fall through to the default [UnknownException] wrapping.
 * @return An [ActionableException] representing this throwable
 * @throws CancellationException if this throwable is a [CancellationException]
 */
inline fun Throwable.toActionableException(
    crossinline mapper: (Throwable) -> ActionableException? = { null }
): ActionableException {
    // 1. Rethrow CancellationException to preserve coroutine cancellation
    if (this is CancellationException) throw this

    // 2. Pass through existing ActionableExceptions unchanged
    if (this is ActionableException) return this

    // 3. Apply custom mapper if provided
    mapper(this)?.let { return it }

    // 4. Fallback to UnknownException
    return UnknownException(error = this)
}
