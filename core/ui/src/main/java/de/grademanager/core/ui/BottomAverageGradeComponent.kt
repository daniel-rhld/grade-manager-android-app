package de.grademanager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.core.designsystem.components.GradeManagerBackground
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme

@Composable
fun BottomAverageGradeComponent(
    label: String,
    grade: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.then(
            other = Modifier.height(AppAssets.sizes.bottomGradeAverageComponentHeight)
        ),
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Row {
                Spacer(
                    modifier = Modifier.width(AppAssets.spacing.screenSpacing)
                )

                Text(
                    text = label,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row {
                GradeBoxComponent(
                    grade = grade
                )

                Spacer(
                    modifier = Modifier.width(AppAssets.spacing.screenSpacing)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewBottomGradeAverageComponent() {
    GradeManagerTheme {
        GradeManagerBackground {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                BottomAverageGradeComponent(
                    label = "Gesamtdurchschnitt:",
                    grade = 4.0,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}