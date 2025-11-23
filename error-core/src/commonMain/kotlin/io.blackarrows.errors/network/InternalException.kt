package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

data class InternalException(
    override val id: String? = "internal_error",
    override val msg: UiMessage = UiMessage.Plain("Internal error."),
    override val severity: ErrorSeverity = ErrorSeverity.Critical,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    error = error
)
