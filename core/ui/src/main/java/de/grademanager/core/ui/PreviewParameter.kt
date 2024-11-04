package de.grademanager.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import de.grademanager.core.model.Grade
import de.grademanager.core.model.Subject
import java.util.Date

class SubjectPreviewParameterProvider : PreviewParameterProvider<Subject> {
    override val values: Sequence<Subject>
        get() = sequenceOf(PreviewParameterData.Subjects[0])
}

class GradePreviewParameterProvider : PreviewParameterProvider<Grade> {
    override val values: Sequence<Grade>
        get() = sequenceOf(PreviewParameterData.Grades[0])
}

object PreviewParameterData {

    val Grades = listOf(
        Grade(
            id = 1,
            grade = 1.0,
            weighting = 1.0,
            description = null,
            receivedAt = Date(),
            createdAt = Date(),
            updatedAt = null,
            deletedAt = null
        ),
        Grade(
            id = 2,
            grade = 3.25,
            weighting = 1.5,
            description = null,
            receivedAt = Date(),
            createdAt = Date(),
            updatedAt = null,
            deletedAt = null
        )
    )

    val Subjects = listOf(
        Subject(
            id = 1,
            name = "Deutsch",
            createdAt = Date(),
            updatedAt = null,
            deletedAt = null,
            grades = Grades
        ),
        Subject(
            id = 2,
            name = "Englisch",
            createdAt = Date(),
            updatedAt = null,
            deletedAt = null,
            grades = Grades
        )
    )

}