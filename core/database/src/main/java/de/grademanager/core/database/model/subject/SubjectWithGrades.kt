package de.grademanager.core.database.model.subject

import androidx.room.Embedded
import androidx.room.Relation
import de.grademanager.core.database.model.grade.GradeEntity
import de.grademanager.core.database.model.grade.asExternalModel

data class SubjectWithGrades(
    @Embedded
    val self: SubjectEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "subject_id"
    )
    val grades: List<GradeEntity>
)

fun SubjectWithGrades.asExternalModel() = self.asExternalModel(
    grades = grades.map(GradeEntity::asExternalModel)
)