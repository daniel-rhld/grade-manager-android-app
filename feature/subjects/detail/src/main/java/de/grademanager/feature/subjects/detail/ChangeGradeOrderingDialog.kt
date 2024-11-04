package de.grademanager.feature.subjects.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.core.designsystem.components.GradeManagerBottomSheet
import de.grademanager.core.designsystem.icons.GradeManagerIcons
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.model.GradeOrdering
import de.grademanager.core.model.OrderingDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeGradeOrderingDialog(
    currentOrdering: GradeOrdering,
    onOrderingSelected: (GradeOrdering) -> Unit,
    onDismissRequested: () -> Unit
) {
    GradeManagerBottomSheet(
        onDismissRequest = onDismissRequested
    ) {
        ChangeGradeOrderingDialogContent(
            currentOrdering = currentOrdering,
            onOrderingSelected = onOrderingSelected
        )
    }
}

@Composable
private fun ChangeGradeOrderingDialogContent(
    currentOrdering: GradeOrdering,
    onOrderingSelected: (GradeOrdering) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(AppAssets.spacing.bottomDialogContentSpacing)
        )

        Text(
            text = stringResource(R.string.feature_subjects_detail_change_grade_ordering_dialog_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        HorizontalDivider()

        GradeOrderingItemComponent(
            label = stringResource(R.string.feature_subjects_detail_change_grade_ordering_dialog_label_received_at),
            active = currentOrdering is GradeOrdering.ReceivedAt,
            direction = (currentOrdering as? GradeOrdering.ReceivedAt)?.direction,
            onClick = {
                onOrderingSelected.invoke(
                    GradeOrdering.ReceivedAt(
                        direction = if (currentOrdering is GradeOrdering.ReceivedAt) {
                            currentOrdering.direction.reverse()
                        } else {
                            OrderingDirection.DESCENDING
                        }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        GradeOrderingItemComponent(
            label = stringResource(R.string.feature_subjects_detail_change_grade_ordering_dialog_label_grade),
            active = currentOrdering is GradeOrdering.Value,
            direction = (currentOrdering as? GradeOrdering.Value)?.direction,
            onClick = {
                onOrderingSelected.invoke(
                    GradeOrdering.Value(
                        direction = if (currentOrdering is GradeOrdering.Value) {
                            currentOrdering.direction.reverse()
                        } else {
                            OrderingDirection.DESCENDING
                        }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GradeOrderingItemComponent(
    label: String,
    active: Boolean,
    direction: OrderingDirection?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val directionIndicatorRotationDegrees by animateFloatAsState(
        targetValue = if (direction == OrderingDirection.ASCENDING) 0F else 180F,
        label = "direction-indicator-rotation-degrees"
    )

    Surface(
        onClick = onClick,
        color = if (active) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppAssets.spacing.bottomDialogContentSpacing,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (active) {
                Icon(
                    imageVector = GradeManagerIcons.StraightArrowUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.rotate(directionIndicatorRotationDegrees)
                )
            } else {
                Spacer(
                    modifier = Modifier.width(24.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (active) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1F)
            )

            if (active) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGradeOrderingItemComponent() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            ChangeGradeOrderingDialogContent(
                currentOrdering = GradeOrdering.Value(
                    direction = OrderingDirection.ASCENDING
                ),
                onOrderingSelected = {}
            )
        }
    }
}