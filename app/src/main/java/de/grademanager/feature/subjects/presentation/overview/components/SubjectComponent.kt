package de.grademanager.feature.subjects.presentation.overview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.core.presentation.components.ClickableCard
import de.grademanager.core.presentation.components.GradeBoxComponent
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.core.presentation.theme.GradeManagerTheme
import de.grademanager.feature.subjects.domain.model.Subject
import de.grademanager.feature.subjects.domain.model.SubjectMock
import de.grademanager.feature.subjects.domain.model.hasAnyGrades

@Composable
fun SubjectComponent(
    subject: Subject,
    averageGrade: Double,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ClickableCard(
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

            if (subject.hasAnyGrades()) {
                GradeBoxComponent(grade = averageGrade)
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
                averageGrade = 1.0,
                onClick = {},
                onLongClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            )
        }
    }
}