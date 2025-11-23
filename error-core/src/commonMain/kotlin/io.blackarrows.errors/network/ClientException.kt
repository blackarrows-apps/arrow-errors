package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorNavigation
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

data class ClientException(
    override val id: String? = "client_error",
    override val msg: UiMessage = UiMessage.Plain("Client error."),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val secondaryAction: ErrorAction? = null,
    override val navigation: ErrorNavigation? = ErrorNavigation.Settings,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    secondaryAction = secondaryAction,
    navigation = navigation,
    error = error
)
