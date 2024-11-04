package de.grademanager.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import de.grademanager.core.designsystem.components.GradeManagerBackground
import de.grademanager.core.designsystem.components.GradeManagerCombinedClickableCard
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.model.Grade
import de.grademanager.core.model.Subject
import java.util.Date

@Composable
fun SubjectComponent(
    subject: Subject,
    averageGrade: Double,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GradeManagerCombinedClickableCard(
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppAssets.spacing.innerCardSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = subject.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1F)
            )

            if (subject.grades.isNotEmpty()) {
                GradeBoxComponent(grade = averageGrade)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSubjectComponent(
    @PreviewParameter(SubjectPreviewParameterProvider::class)
    subject: Subject
) {
    GradeManagerTheme {
        GradeManagerBackground {
            Spacer(
                modifier = Modifier.height(AppAssets.spacing.screenSpacing)
            )

            SubjectComponent(
                subject = subject,
                averageGrade = 1.0,
                onClick = {},
                onLongClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
            )

            SubjectComponent(
                subject = subject.copy(name = "Englisch"),
                averageGrade = 3.0,
                onClick = {},
                onLongClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
            )

            SubjectComponent(
                subject = subject.copy(name = "Mathematik"),
                averageGrade = 5.0,
                onClick = {},
                onLongClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.screenSpacing)
            )
        }
    }
}