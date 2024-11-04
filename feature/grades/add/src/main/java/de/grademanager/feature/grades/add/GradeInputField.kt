package de.grademanager.feature.grades.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.ui.isBadGrade
import de.grademanager.core.ui.isGoodGrade
import de.grademanager.core.ui.isModerateGrade

private val FontSize = 28.sp

@Composable
fun GradeInputField(
    grade: String,
    onGradeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val gradeValue = try {
        grade.replace(",", ".").toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }

    val gradeContainerColor = when {
        gradeValue.isGoodGrade() -> AppAssets.colors.gradeStatusColors.goodContainer
        gradeValue.isModerateGrade() -> AppAssets.colors.gradeStatusColors.moderateContainer
        gradeValue.isBadGrade() -> AppAssets.colors.gradeStatusColors.badContainer
        else -> MaterialTheme.colorScheme.surfaceContainerHighest
    }

    val gradeContentColor = when {
        gradeValue.isGoodGrade() -> AppAssets.colors.gradeStatusColors.onGoodContainer
        gradeValue.isModerateGrade() -> AppAssets.colors.gradeStatusColors.onModerateContainer
        gradeValue.isBadGrade() -> AppAssets.colors.gradeStatusColors.onBadContainer
        else -> MaterialTheme.colorScheme.onSurface
    }



    BasicTextField(
        modifier = modifier.then(
            other = Modifier.width(
                width = with(density) {
                    // FontSize * [Maximum amount of characters to appear]
                    FontSize.toDp() * 4
                }
            )
        ),
        value = grade,
        onValueChange = { value ->
            // TODO: This logic works currently, but there must be a way to make it better
            if (value.isNotBlank()) {
                try {
                    val doubleValue = value
                        .replace(",", ".")
                        .toDouble()

                    if (doubleValue in 1.0..6.0) {
                        if (value.replace(".", ",").contains(",")) {
                            val afterComma = value.replace(".", ",").substringAfterLast(",")
                            val newAfterComma = if (afterComma.length > 2) {
                                afterComma.substring(0, 2)
                            } else {
                                afterComma
                            }

                            val newValue = value.replace(".", ",").replaceAfterLast(",", newAfterComma)
                            onGradeChange.invoke(newValue)
                        } else {
                            onGradeChange.invoke(value)
                        }
                    }
                } catch (_: NumberFormatException) {}
            } else {
                onGradeChange.invoke(value)
            }
        },
        cursorBrush = SolidColor(gradeContentColor),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        textStyle = TextStyle(
            fontSize = FontSize,
            textAlign = TextAlign.Center,
            color = gradeContentColor,
            fontWeight = FontWeight.Bold
        ),
        decorationBox = { innerTextField ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = gradeContainerColor,
                    contentColor = gradeContentColor
                ),
                shape = RoundedCornerShape(size = 12.dp)
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    innerTextField.invoke()
                }
            }
        }
    )
}

class GradeInputFieldPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf(
            "1,0",
            "3,0",
            "5,0"
        )
}

@PreviewLightDark
@Composable
private fun PreviewGradeInputField(
    @PreviewParameter(GradeInputFieldPreviewParameterProvider::class)
    grade: String
) {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            GradeInputField(
                grade = grade,
                onGradeChange = {}
            )
        }
    }
}