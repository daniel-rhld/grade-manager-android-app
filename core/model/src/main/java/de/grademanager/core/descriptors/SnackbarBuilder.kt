package de.grademanager.core.descriptors

import de.grademanager.common.util.StringWrapper

class SnackbarBuilder(
    var message: StringWrapper = StringWrapper.Value(""),
    var type: SnackbarType = SnackbarType.INFO,
    var action: SnackbarAction? = null,
    var duration: SnackbarDuration = SnackbarDuration.SHORT
) {
    fun action(builder: SnackbarAction.() -> Unit) {
        action = SnackbarAction()
        action?.let(builder)
    }
}

enum class SnackbarType {
    ERROR, INFO;
}

data class SnackbarAction(
    var label: StringWrapper = StringWrapper.Value(""),
    var action: suspend () -> Unit = {}
)

enum class SnackbarDuration {
    LONG, SHORT, INDEFINITE;
}