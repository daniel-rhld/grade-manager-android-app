package de.grademanager.feature.subjects.domain.repository

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity
import de.grademanager.feature.subjects.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    fun getAllExistingSubjectsOrdered(
        orderColumn: String,
        orderAscending: Boolean
    ): Flow<List<Subject>>

    suspend fun doesSubjectAlreadyExist(name: String): Boolean

    suspend fun findById(id: Int): DataResult<Subject>

    suspend fun upsert(value: SubjectEntity): DataResult<Unit>

    suspend fun delete(subjectId: Int): DataResult<Unit>

}