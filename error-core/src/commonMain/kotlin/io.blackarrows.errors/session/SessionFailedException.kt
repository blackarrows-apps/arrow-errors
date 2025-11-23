package io.blackarrows.errors.session

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

data class SessionFailedException(
    override val id: String? = "session_failed",
    override val msg: UiMessage = UiMessage.Plain("Session details could not be found, please log in again."),
    override val severity: ErrorSeverity = ErrorSeverity.Critical,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Dismiss,
    override val navigation: ErrorNavigation? = ErrorNavigation.Login,
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
