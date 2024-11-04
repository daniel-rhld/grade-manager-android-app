package de.grademanager.core.data.repository.grade

import de.grademanager.common.util.DataResult
import de.grademanager.core.database.model.grade.GradeEntity
import de.grademanager.core.model.Grade
import de.grademanager.core.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

interface GradeRepository {

    fun getAllGradesForSubject(subjectId: Int): Flow<List<Grade>>

    suspend fun upsert(entity: GradeEntity): DataResult<Unit>

    suspend fun deleteById(id: Int): DataResult<Unit>

    suspend fun restoreById(id: Int): DataResult<Unit>

    fun getGradeOrderingSettings(): Flow<GradeOrdering>

    suspend fun updateGradeOrdering(value: GradeOrdering)

}