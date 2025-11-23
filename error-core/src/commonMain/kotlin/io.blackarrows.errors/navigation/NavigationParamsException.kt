package io.blackarrows.errors.navigation

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorNavigation

data class NavigationParamsException(
    override var msg: String = "Navigation parameters are invalid",
    override var navigate: ErrorNavigation? = ErrorNavigation.BACK,
    override var error: Throwable? = null,
) : ActionableException(
        msg = msg,
        error = error,
        navigate = navigate,
    )
