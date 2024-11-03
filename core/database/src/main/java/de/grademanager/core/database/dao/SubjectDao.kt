package de.grademanager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import de.grademanager.core.database.model.subject.SubjectEntity
import de.grademanager.core.database.model.subject.SubjectWithGrades
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Transaction
    @Query("""
        SELECT s.* FROM subjects s WHERE s.deleted_at IS NULL ORDER BY
        CASE WHEN :orderAscending = 1 THEN :orderColumn END ASC,
        CASE WHEN :orderAscending = 0 THEN :orderColumn END DESC
    """)
    fun getAllExistingSubjectsWithGradesOrdered(
        orderColumn: String,
        orderAscending: Boolean = true
    ): Flow<List<SubjectWithGrades>>

    @Query("SELECT COUNT(*) FROM subjects s WHERE s.name = :name")
    suspend fun doesSubjectAlreadyExist(name: String): Int

    @Transaction
    @Query("SELECT s.* FROM subjects s WHERE id = :id")
    suspend fun findById(id: Int): SubjectWithGrades?

    @Upsert
    suspend fun upsert(value: SubjectEntity)

    @Query("UPDATE subjects SET deleted_at = :deletionTimestamp WHERE id = :subjectId")
    suspend fun delete(
        subjectId: Int,
        deletionTimestamp: Long
    )

}