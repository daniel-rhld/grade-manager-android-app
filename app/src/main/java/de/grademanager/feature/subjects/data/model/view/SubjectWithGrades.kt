package de.grademanager.feature.subjects.data.model.view

import androidx.room.Embedded
import androidx.room.Relation
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.data.model.entity.mapToDomainObject
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity
import de.grademanager.feature.subjects.data.model.entity.mapToDomainObject

data class SubjectWithGrades(
    @Embedded
    val self: SubjectEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "subject_id"
    )
    val grades: List<GradeEntity>
)

fun SubjectWithGrades.mapToDomainObject() = self.mapToDomainObject(
    grades = grades.map(GradeEntity::mapToDomainObject)
)