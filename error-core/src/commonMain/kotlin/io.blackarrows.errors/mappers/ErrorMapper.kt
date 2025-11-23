package io.blackarrows.errors.mappers

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.session.SessionFailedException
import io.blackarrows.errors.network.AuthException
import io.blackarrows.errors.network.ClientException
import io.blackarrows.errors.network.InternalException
import io.blackarrows.errors.network.NetworkException
import io.blackarrows.errors.network.StorageException
import io.blackarrows.errors.network.UnknownException
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

/**
 * Maps a [Throwable] to an [ActionableException], providing structured error handling and logging.
 *
 * This function serves as a centralized place to translate unexpected or system-level exceptions
 * (e.g., [IOException], [CancellationException], unknown [Throwable]) into custom domain-specific
 * exceptions that can be handled predictably across the application (e.g., [NetworkException], [SessionFailedException]).
 *
 * The mapping behavior includes:
 * - Passing through known [ActionableException] types unmodified (to prevent double-wrapping).
 * - Converting [IOException] into [NetworkException] or [StorageException], based on the `isNetwork` flag.
 * - Returning [CancellationException] as-is to avoid interfering with coroutine cancellation.
 * - Delegating all other unknown errors to the provided [default] factory lambda.
 * @param isNetwork Whether the [IOException] should be treated as a network error (`true` = [NetworkException]).
 * @param default A fallback factory lambda to convert unknown exceptions into an [ActionableException].
 *                It will not be called if the throwable is already an [ActionableException].
 *
 * @return A mapped [Throwable] — either the original if already an actionable type, or a wrapped [ActionableException].
 */
fun Throwable.mapError(
    isNetwork: Boolean = false,
    default: (Throwable) -> ActionableException,
): Throwable =
    when (this) {
        is CancellationException -> this
        is IOException -> {
            if (isNetwork) {
                NetworkException(error = this)
            } else {
                StorageException(error = this)
            }
        }
        is ClientException -> this
        is AuthException -> this
        is NetworkException -> this
        is InternalException -> this
        is UnknownException -> this
        is SessionFailedException -> this
        else -> {
            val mapped = default(this)
            if (this::class == mapped::class) this else mapped
        }
    }
