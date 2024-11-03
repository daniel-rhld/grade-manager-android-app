package de.grademanager.core.database.model.subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.grademanager.core.model.Grade
import de.grademanager.core.model.Subject
import java.util.Date

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date? = null,

    @ColumnInfo(name = "deleted_at")
    val deletedAt: Date? = null
)

fun SubjectEntity.asExternalModel(grades: List<Grade> = emptyList()) = Subject(
    id = id,
    name = name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    grades = grades
)