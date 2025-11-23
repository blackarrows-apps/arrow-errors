package io.blackarrows.errors

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

data class EmptyListException(
    override val id: String? = "empty_list",
    override val msg: UiMessage = UiMessage.Plain("The list is empty, please add some data and try again."),
    override val severity: ErrorSeverity = ErrorSeverity.Warning,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val navigation: ErrorNavigation? = ErrorNavigation.Back,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    navigation = navigation,
    error = error
)
