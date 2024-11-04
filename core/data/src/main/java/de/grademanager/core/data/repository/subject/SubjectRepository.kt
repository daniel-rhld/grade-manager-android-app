package de.grademanager.core.data.repository.subject

import de.grademanager.common.util.DataResult
import de.grademanager.core.database.model.subject.SubjectEntity
import de.grademanager.core.model.Subject
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