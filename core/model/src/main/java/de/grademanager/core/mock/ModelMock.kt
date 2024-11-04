package de.grademanager.core.mock

import de.grademanager.core.model.Grade
import de.grademanager.core.model.GradeOrdering
import de.grademanager.core.model.OrderingDirection
import de.grademanager.core.model.Subject
import de.grademanager.core.model.SubjectWithAverageGrade
import java.util.Date

typealias ModelGradeOrdering = de.grademanager.core.model.GradeOrdering

object ModelMock {

    val Grade = Grade(
        id = 1,
        grade = 1.0,
        weighting = 1.0,
        description = null,
        receivedAt = Date(),
        createdAt = Date(),
        updatedAt = null,
        deletedAt = null
    )

    val Subject = Subject(
        id = 1,
        name = "Deutsch",
        createdAt = Date(),
        updatedAt = null,
        deletedAt = null,
        grades = listOf(
            Grade,
            Grade.copy(id = 2, grade = 3.0),
            Grade.copy(id = 3, grade = 5.0)
        )
    )

    val SubjectWithAverageGrade = SubjectWithAverageGrade(
        self = Subject,
        averageGrade = 1.75
    )

    val ValueGradeOrdering = GradeOrdering.Value(
        direction = OrderingDirection.DESCENDING
    )
    val ReceivedAtGradeOrdering = GradeOrdering.ReceivedAt(
        direction = OrderingDirection.DESCENDING
    )



}