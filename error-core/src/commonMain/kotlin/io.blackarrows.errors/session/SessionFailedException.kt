package io.blackarrows.errors.base.session

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorActions
import io.blackarrows.errors.base.ErrorNavigation

data class SessionFailedException(
    override var msg: String = "Session details could not be found, please log in again.",
    override var action: ErrorActions? = ErrorActions.DISMISS,
    override var navigate: ErrorNavigation? = ErrorNavigation.LOGIN,
    override var error: Throwable? = null,
) : ActionableException(msg, error, action, navigate)
