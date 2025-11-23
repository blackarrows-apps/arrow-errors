package io.blackarrows.errors.base

open class ActionableException(
    open val id: String? = null,
    open val msg: UiMessage = UiMessage.Plain("An Error has occurred, please try again later."),
    open val severity: ErrorSeverity = ErrorSeverity.Error,
    open val presentation: ErrorPresentation = ErrorPresentation.Dialog,
    open val primaryAction: ErrorAction? = null,
    open val secondaryAction: ErrorAction? = null,
    open val navigation: ErrorNavigation? = null,
    open val error: Throwable? = null,
) : Exception(
    when (msg) {
        is UiMessage.Plain -> msg.text
        is UiMessage.ResourceId -> msg.resId
        is UiMessage.Formatted -> msg.template
    },
    error
)
