package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorActions
import io.blackarrows.errors.base.ErrorNavigation

data class AuthException(
    override var msg: String = "Authentication error.",
    override var error: Throwable? = null,
    override var action: ErrorActions? = ErrorActions.RETRY,
    override var navigate: ErrorNavigation? = ErrorNavigation.LOGIN,
) : ActionableException(msg, error, action, navigate)
