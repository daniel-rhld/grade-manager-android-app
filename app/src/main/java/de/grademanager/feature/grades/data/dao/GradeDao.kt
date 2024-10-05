package de.grademanager.feature.grades.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeDao {

    @Query("SELECT * FROM grades g WHERE g.subject_id = :subjectId AND g.deleted_at IS NULL")
    fun getAllGradesForSubject(subjectId: Int): Flow<List<GradeEntity>>

    @Upsert
    suspend fun upsert(entity: GradeEntity)

    @Query("UPDATE grades SET deleted_at = :deletionTimestamp WHERE id = :id")
    suspend fun deleteById(id: Int, deletionTimestamp: Long)

}