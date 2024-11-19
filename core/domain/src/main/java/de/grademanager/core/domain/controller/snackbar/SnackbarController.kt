package de.grademanager.core.domain.controller.snackbar

import de.grademanager.core.descriptors.SnackbarBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface SnackbarController {
    val snackbarData: Flow<SnackbarBuilder>

    suspend fun showSnackbar(builder: SnackbarBuilder.() -> Unit)
}

class SnackbarControllerImpl : SnackbarController {

    private var _snackbarData = Channel<SnackbarBuilder>()

    override val snackbarData: Flow<SnackbarBuilder>
        get() = _snackbarData.receiveAsFlow()

    override suspend fun showSnackbar(builder: SnackbarBuilder.() -> Unit) {
        val snackbarBuilder = SnackbarBuilder()
        builder.invoke(snackbarBuilder)

        _snackbarData.send(snackbarBuilder)
    }
}