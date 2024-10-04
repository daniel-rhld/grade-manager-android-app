package de.grademanager.feature.subjects.presentation.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.core.domain.ext.formatGrade
import de.grademanager.core.domain.ext.isBadGrade
import de.grademanager.core.domain.ext.isGoodGrade
import de.grademanager.core.domain.ext.isModerateGrade
import de.grademanager.core.presentation.components.ClickableCard
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.core.presentation.theme.GradeManagerTheme
import de.grademanager.feature.subjects.domain.models.Subject
import de.grademanager.feature.subjects.domain.models.SubjectMock
import de.grademanager.feature.subjects.domain.models.calculateAverageGrade
import de.grademanager.feature.subjects.domain.models.hasAnyGrades

@Composable
fun SubjectComponent(
    subject: Subject,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val averageGrade = remember { subject.calculateAverageGrade() }

    ClickableCard(
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = subject.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1F)
            )

            if (subject.hasAnyGrades()) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(size = 6.dp)
                        ).background(
                            color = when {
                                averageGrade.isGoodGrade() ->
                                    AppAssets.colors.gradeStatusColors.goodContainer

                                averageGrade.isModerateGrade() ->
                                    AppAssets.colors.gradeStatusColors.moderateContainer

                                averageGrade.isBadGrade() ->
                                    AppAssets.colors.gradeStatusColors.badContainer

                                else -> Color.Transparent
                            }
                        )
                ) {
                    Text(
                        text = averageGrade.formatGrade(),
                        color = when {
                            averageGrade.isGoodGrade() ->
                                AppAssets.colors.gradeStatusColors.onGoodContainer

                            averageGrade.isModerateGrade() ->
                                AppAssets.colors.gradeStatusColors.onModerateContainer

                            averageGrade.isBadGrade() ->
                                AppAssets.colors.gradeStatusColors.onBadContainer

                            else -> Color.Transparent
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(all = 4.dp)
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSubjectComponent() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SubjectComponent(
                subject = SubjectMock,
                onClick = {},
                onLongClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            )
        }
    }
}