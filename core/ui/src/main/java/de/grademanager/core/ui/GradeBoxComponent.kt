package de.grademanager.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import de.grademanager.core.designsystem.components.GradeManagerBackground
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import java.util.Locale

fun Double.isGoodGrade() = this >= 1.0 && this < 2.5
fun Double.isModerateGrade() = this >= 2.5 && this < 4.5
fun Double.isBadGrade() = this in 4.5..6.0

fun Double.formatGrade(): String {
    val decimalPlacesModifier = when {
        this.toBigDecimal() % 1.0.toBigDecimal() == 0.0.toBigDecimal() -> 0
        this.toBigDecimal() % 0.1.toBigDecimal() == 0.0.toBigDecimal() -> 1
        else -> 2
    }

    return String.format(
        locale = Locale.getDefault(),
        format = "%.${decimalPlacesModifier}f",
        this
    )
}

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
private fun PreviewGradeBoxComponent() {
    GradeManagerTheme {
        GradeManagerBackground {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier.height(AppAssets.spacing.screenSpacing)
                )

                GradeBoxComponent(grade = 1.0)

                Spacer(
                    modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
                )

                GradeBoxComponent(grade = 3.0)

                Spacer(
                    modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
                )

                GradeBoxComponent(grade = 5.0)

                Spacer(
                    modifier = Modifier.height(AppAssets.spacing.screenSpacing)
                )
            }
        }
    }
}