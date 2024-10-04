package de.grademanager.feature.subjects.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity
import de.grademanager.feature.subjects.data.model.view.SubjectWithGrades
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Query("""
        SELECT s.* FROM subjects s WHERE s.deleted_at IS NULL ORDER BY
        CASE WHEN :orderAscending = 1 THEN :orderColumn END ASC,
        CASE WHEN :orderAscending = 0 THEN :orderColumn END DESC
    """)
    fun getAllExistingSubjectsWithGradesOrdered(
        orderColumn: String,
        orderAscending: Boolean = true
    ): Flow<List<SubjectWithGrades>>

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