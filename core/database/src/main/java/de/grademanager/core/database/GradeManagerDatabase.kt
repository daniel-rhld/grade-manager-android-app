package de.grademanager.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.grademanager.core.database.converters.DateConverter
import de.grademanager.core.database.dao.GradeDao
import de.grademanager.core.database.dao.SubjectDao
import de.grademanager.core.database.model.grade.GradeEntity
import de.grademanager.core.database.model.subject.SubjectEntity

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