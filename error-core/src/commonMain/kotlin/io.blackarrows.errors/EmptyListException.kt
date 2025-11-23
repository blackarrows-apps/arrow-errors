package io.blackarrows.errors

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorActions
import io.blackarrows.errors.base.ErrorNavigation

data class EmptyListException(
    override var msg: String = "The list is empty, please add some data and try again.",
    override var error: Throwable? = null,
    override var action: ErrorActions? = ErrorActions.RETRY,
    override var navigate: ErrorNavigation? = ErrorNavigation.BACK,
) : ActionableException(msg, error, action, navigate)
