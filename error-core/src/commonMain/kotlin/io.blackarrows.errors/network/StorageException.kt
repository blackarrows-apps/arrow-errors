package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorActions

data class StorageException(
    override var msg: String = "Error, retrieving your details, please check your storage capacity",
    override var error: Throwable? = null,
    override var action: ErrorActions? = ErrorActions.RETRY,
) : ActionableException(msg, error, action)
