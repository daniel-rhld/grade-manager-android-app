package de.grademanager.feature.subjects.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.grademanager.feature.grades.domain.model.Grade
import de.grademanager.feature.subjects.domain.model.Subject
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

fun SubjectEntity.mapToDomainObject(grades: List<Grade>) = Subject(
    id = id,
    name = name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    grades = grades.filter { it.deletedAt == null }
)