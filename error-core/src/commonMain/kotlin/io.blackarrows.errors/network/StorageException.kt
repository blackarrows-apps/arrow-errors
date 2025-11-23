package io.blackarrows.errors.network

import io.blackarrows.errors.base.ActionableException
import io.blackarrows.errors.base.ErrorAction
import io.blackarrows.errors.base.ErrorPresentation
import io.blackarrows.errors.base.ErrorSeverity
import io.blackarrows.errors.base.UiMessage

data class StorageException(
    override val id: String? = "storage_error",
    override val msg: UiMessage = UiMessage.Plain("Error, retrieving your details, please check your storage capacity"),
    override val severity: ErrorSeverity = ErrorSeverity.Error,
    override val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    override val primaryAction: ErrorAction? = ErrorAction.Retry,
    override val error: Throwable? = null,
) : ActionableException(
    id = id,
    msg = msg,
    severity = severity,
    presentation = presentation,
    primaryAction = primaryAction,
    error = error
)
