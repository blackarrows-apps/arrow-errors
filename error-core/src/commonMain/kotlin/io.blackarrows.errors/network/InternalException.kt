package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException

data class InternalException(
    override var msg: String = "Internal error.",
    override var error: Throwable? = null,
) : ActionableException(msg, error, action = null)
