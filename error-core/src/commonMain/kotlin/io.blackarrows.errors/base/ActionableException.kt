package io.blackarrows.errors.base

open class ActionableException(
    open var msg: String = "An Error has occurred, please try again later.",
    open var error: Throwable? = null,
    open var action: ErrorActions? = null,
    open var navigate: ErrorNavigation? = null,
) : Exception(msg, error)
