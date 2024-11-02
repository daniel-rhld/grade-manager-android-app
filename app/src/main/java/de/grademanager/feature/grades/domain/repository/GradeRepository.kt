package de.grademanager.feature.grades.domain.repository

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.domain.model.Grade
import de.grademanager.feature.subjects.domain.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

interface GradeRepository {

    fun getAllGradesForSubject(subjectId: Int): Flow<List<Grade>>

    suspend fun upsert(entity: GradeEntity): DataResult<Unit>

    suspend fun deleteById(id: Int): DataResult<Unit>

    suspend fun restoreById(id: Int): DataResult<Unit>

    fun getGradeOrderingSettings(): Flow<GradeOrdering>

    suspend fun updateGradeOrdering(value: GradeOrdering)

}