package de.grademanager.feature.grades.data.repository

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.domain.models.Grade
import kotlinx.coroutines.flow.Flow

interface GradeRepository {

    fun getAllGradesForSubject(subjectId: Int): Flow<List<Grade>>

    suspend fun upsert(entity: GradeEntity): DataResult<Unit>

    suspend fun deleteById(id: Int): DataResult<Unit>

}