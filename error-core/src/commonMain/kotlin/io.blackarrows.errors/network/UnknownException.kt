package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException

data class UnknownException(
    override var msg: String = "An unknown error occurred.",
    override var error: Throwable? = null,
) : ActionableException(msg, error)
