package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException

data class JsonDecodingException(
    override var msg: String = "Error decoding data.",
    override var error: Throwable? = null,
) : ActionableException(msg, error)
