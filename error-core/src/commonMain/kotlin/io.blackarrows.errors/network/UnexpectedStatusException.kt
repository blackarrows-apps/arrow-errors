package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException

data class UnexpectedStatusException(
    override var msg: String = "An unknown error occurred.",
    override var error: Throwable? = null,
) : ActionableException(msg, error)
