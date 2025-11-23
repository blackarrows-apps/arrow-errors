package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException

data class ContentDecodingException(
    override var msg: String = "Error decoding content.",
    override var error: Throwable? = null,
) : ActionableException(msg, error)
