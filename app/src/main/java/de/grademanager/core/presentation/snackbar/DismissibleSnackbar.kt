package de.grademanager.core.presentation.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DismissibleSnackbar(
    snackbarData: SnackbarData,
    padding: PaddingValues,
    onDismiss: () -> Unit,
    containerColor: Color? = null,
    contentColor: Color? = null,
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled
    )
    var shouldSwipeToDismissBoxVisible by remember { mutableStateOf(value = true) }

    LaunchedEffect(key1 = swipeToDismissBoxState.currentValue) {
        if(swipeToDismissBoxState.currentValue != SwipeToDismissBoxValue.Settled) {
            shouldSwipeToDismissBoxVisible = false
            onDismiss.invoke()
        }
    }

    if (shouldSwipeToDismissBoxVisible) {
        SwipeToDismissBox(
            state = swipeToDismissBoxState,
            backgroundContent = {},
            content = {
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = containerColor ?: SnackbarDefaults.color,
                    contentColor = contentColor ?: SnackbarDefaults.contentColor,
                    actionContentColor = contentColor ?: SnackbarDefaults.contentColor,
                    actionOnNewLine = true,
                    shape = RoundedCornerShape(size = 8.dp),
                    modifier = Modifier.fillMaxWidth().padding(padding)
                )
            }
        )
    }
}