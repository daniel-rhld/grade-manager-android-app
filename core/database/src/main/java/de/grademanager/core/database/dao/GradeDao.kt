package de.grademanager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.grademanager.core.database.model.grade.GradeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeDao {

    @Query("""
        SELECT * FROM grades g WHERE g.subject_id = :subjectId AND g.deleted_at IS NULL
    """)
    fun getAllGradesForSubject(subjectId: Int): Flow<List<GradeEntity>>

    @Upsert
    suspend fun upsert(entity: GradeEntity)

    @Query("UPDATE grades SET deleted_at = :deletionTimestamp WHERE id = :id")
    suspend fun deleteById(id: Int, deletionTimestamp: Long)

    @Query("UPDATE grades SET deleted_at = NULL WHERE id = :id")
    suspend fun restoreById(id: Int)

}