package de.grademanager.feature.grades.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.grademanager.feature.grades.domain.models.Grade
import java.util.Date

@Entity(tableName = "grades")
data class GradeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "grade")
    val grade: Double,

    @ColumnInfo(name = "weighting")
    val weighting: Double,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "subject_id")
    val subjectId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date?,

    @ColumnInfo(name = "deleted_at")
    val deletedAt: Date?
)

fun GradeEntity.mapToDomainObject() = Grade(
    id = id,
    grade = grade,
    weighting = weighting,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)