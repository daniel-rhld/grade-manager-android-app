package de.grademanager.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.core.domain.ext.formatGrade
import de.grademanager.core.domain.ext.isBadGrade
import de.grademanager.core.domain.ext.isGoodGrade
import de.grademanager.core.domain.ext.isModerateGrade
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.core.presentation.theme.GradeManagerTheme

@Composable
fun GradeBoxComponent(
    grade: Double,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var boxHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier.then(
            other = Modifier.clip(
                shape = RoundedCornerShape(size = 6.dp)
            ).widthIn(
                min = boxHeight
            ).background(
                color = when {
                    grade.isGoodGrade() ->
                        AppAssets.colors.gradeStatusColors.goodContainer

                    grade.isModerateGrade() ->
                        AppAssets.colors.gradeStatusColors.moderateContainer

                    grade.isBadGrade() ->
                        AppAssets.colors.gradeStatusColors.badContainer

                    else -> Color.Transparent
                }
            ).onSizeChanged { size ->
                with(density) {
                    size.height.toDp()
                }.let { height ->
                    if (height > boxHeight) {
                        boxHeight = height
                    }
                }
            }
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = grade.formatGrade(),
            color = when {
                grade.isGoodGrade() ->
                    AppAssets.colors.gradeStatusColors.onGoodContainer

                grade.isModerateGrade() ->
                    AppAssets.colors.gradeStatusColors.onModerateContainer

                grade.isBadGrade() ->
                    AppAssets.colors.gradeStatusColors.onBadContainer

                else -> Color.Transparent
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(all = 4.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewGradeBoxComponentGood() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            GradeBoxComponent(
                grade = 1.0,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGradeBoxComponentModerate() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            GradeBoxComponent(
                grade = 3.54987,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGradeBoxComponentBad() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            GradeBoxComponent(
                grade = 5.0,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}