package de.grademanager.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import de.grademanager.core.designsystem.icons.GradeManagerIcons
import de.grademanager.core.designsystem.theme.GradeManagerTheme

@Composable
fun GradeManagerTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    text: @Composable () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding
    ) {
        GradeManagerTextButtonContent(
            text = text,
            leadingIcon = null
        )
    }
}

@Composable
fun GradeManagerTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    text: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding
    ) {
        GradeManagerTextButtonContent(
            text = text,
            leadingIcon = leadingIcon
        )
    }
}

@Composable
private fun GradeManagerTextButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    if (leadingIcon != null) {
        Box(
            modifier = Modifier.sizeIn(
                maxHeight = ButtonDefaults.IconSize
            )
        ) {
            leadingIcon()
        }
    }

    Box(
        modifier = Modifier.padding(
            start = if (leadingIcon != null) {
                ButtonDefaults.IconSpacing
            } else {
                0.dp
            }
        )
    ) {
        text()
    }
}

@PreviewLightDark
@Composable
private fun PreviewGradeManagerTextButton() {
    GradeManagerTheme {
        GradeManagerBackground {
            GradeManagerTextButton(
                onClick = {},
                text = {
                    Text(
                        text = "Test"
                    )
                }
            )

            GradeManagerTextButton(
                onClick = {},
                text = {
                    Text(
                        text = "Test"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = GradeManagerIcons.Add,
                        contentDescription = null
                    )
                }
            )
        }
    }
}