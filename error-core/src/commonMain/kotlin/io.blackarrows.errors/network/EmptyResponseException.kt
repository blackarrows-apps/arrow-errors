package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException

data class EmptyResponseException(
    override var msg: String = "The response body was empty.",
    override var error: Throwable? = null,
) : ActionableException(msg, error)
