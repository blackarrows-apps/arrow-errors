package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException

data class UnsupportedMediaTypeException(
    override var msg: String = "Unsupported or missing data Content-Type",
    override var error: Throwable? = null,
) : ActionableException(msg, error)
