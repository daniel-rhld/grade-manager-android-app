package de.grademanager.core.database.model.grade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.grademanager.core.model.Grade
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

    @ColumnInfo(name = "received_at")
    val receivedAt: Date,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date? = null,

    @ColumnInfo(name = "deleted_at")
    val deletedAt: Date? = null
)

fun GradeEntity.asExternalModel() = Grade(
    id = id,
    grade = grade,
    weighting = weighting,
    description = description,
    receivedAt = receivedAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)