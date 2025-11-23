package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorActions

data class NetworkException(
    override var msg: String = "Network error occurred.",
    override var error: Throwable? = null,
    override var action: ErrorActions? = ErrorActions.RETRY,
) : ActionableException(msg, error, action)
