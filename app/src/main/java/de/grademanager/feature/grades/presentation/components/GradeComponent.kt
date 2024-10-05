package de.grademanager.feature.grades.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import de.grademanager.R
import de.grademanager.core.presentation.components.GradeBoxComponent
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.core.presentation.theme.GradeManagerTheme
import de.grademanager.feature.grades.domain.models.Grade
import de.grademanager.feature.subjects.domain.models.SubjectMock
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GradeComponent(
    grade: Grade,
    onClick: () -> Unit,
    onDeleteRequested: () -> Unit,
    horizontalPadding: Dp,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val density = LocalDensity.current

    val swipeToDismissState = rememberSwipeToDismissBoxState()
    var cardHeight by remember { mutableStateOf(0.dp) }

    LaunchedEffect(key1 = swipeToDismissState.currentValue) {
        if (swipeToDismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            onDeleteRequested.invoke()
            swipeToDismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismissBox(
        state = swipeToDismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = horizontalPadding
                    ),
                horizontalArrangement = Arrangement.End
            ) {
                Card(
                    modifier = Modifier
                        .width(
                            width = max(
                                a = cardHeight,
                                b = screenWidth * swipeToDismissState.progress
                            )
                        )
                        .height(cardHeight),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.width(16.dp)
                )
            }
        }
    ) {
        Card(
            modifier = modifier.then(
                other = Modifier.padding(
                    horizontal = horizontalPadding
                ).onSizeChanged { size ->
                    with(density) {
                        size.height.toDp()
                    }.let { height ->
                        if (height > cardHeight) {
                            cardHeight = height
                        }
                    }
                }
            ),
            onClick = onClick,
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppAssets.spacing.innerCardSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GradeBoxComponent(
                    grade = grade.grade
                )

                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = SimpleDateFormat(
                            stringResource(R.string.date_format_short),
                            Locale.getDefault()
                        ).format(grade.receivedAt),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = grade.description ?: stringResource(
                            id = R.string.subject_detail_grade_label_no_description
                        ),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGradeComponent() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            GradeComponent(
                grade = SubjectMock.grades.first(),
                onClick = {},
                onDeleteRequested = {},
                horizontalPadding = 24.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}