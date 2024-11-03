package de.grademanager.core.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import de.grademanager.core.designsystem.theme.GradeManagerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GradeManagerCombinedClickableCard(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(size = 12.dp)
            )
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(
            size = 12.dp
        ),
        content = content
    )
}

@PreviewLightDark
@Composable
private fun PreviewGradeManagerCombinedClickableCard() {
    GradeManagerTheme {
        GradeManagerBackground {
            GradeManagerCombinedClickableCard(
                onClick = {},
                onLongClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = "Content",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp)
                )
            }
        }
    }
}