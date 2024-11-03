package de.grademanager.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import de.grademanager.core.designsystem.theme.GradeManagerTheme

@Composable
fun GradeManagerBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGradeManagerBackground() {
    GradeManagerTheme {
        GradeManagerBackground {

        }
    }
}