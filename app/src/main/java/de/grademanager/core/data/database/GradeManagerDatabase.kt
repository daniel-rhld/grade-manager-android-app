package de.grademanager.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.grademanager.core.data.database.converters.DateConverter
import de.grademanager.feature.grades.data.dao.GradeDao
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.subjects.data.dao.SubjectDao
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity

@Database(
    entities = [
        SubjectEntity::class,
        GradeEntity::class
    ],
    version = 1
)
@TypeConverters(
    DateConverter::class
)
abstract class GradeManagerDatabase : RoomDatabase() {
    abstract fun getSubjectDao(): SubjectDao
    abstract fun getGradeDao(): GradeDao
}