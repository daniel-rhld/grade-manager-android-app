package de.grademanager.core.data.repository.subject

import de.grademanager.common.util.DataResult
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.database.dao.SubjectDao
import de.grademanager.core.database.model.subject.SubjectEntity
import de.grademanager.core.database.model.subject.asExternalModel
import de.grademanager.core.model.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepositoryImpl(
    private val subjectDao: SubjectDao
) : SubjectRepository {
    override fun getAllExistingSubjectsOrdered(
        orderColumn: String,
        orderAscending: Boolean
    ): Flow<List<Subject>> {
        return subjectDao.getAllExistingSubjectsWithGradesOrdered(
            orderColumn = orderColumn,
            orderAscending = orderAscending
        ).map { list ->
            list.map { subjectWithGrades ->
                subjectWithGrades.asExternalModel()
            }
        }
    }

    override suspend fun doesSubjectAlreadyExist(name: String): Boolean {
        val count = subjectDao.doesSubjectAlreadyExist(name)
        return count >= 1
    }

    override suspend fun findById(id: Int): DataResult<Subject> {
        return subjectDao.findById(id = id).let { subject ->
            if (subject != null) {
                DataResult.Success(subject.asExternalModel())
            } else {
                DataResult.Failure(
                    error = "Subject not found".asStringWrapper()
                )
            }
        }
    }

    override fun findByIdAsFlow(id: Int): Flow<DataResult<Subject>> {
        return subjectDao.findByIdAsFlow(id = id).map { subject ->
            if (subject != null) {
                DataResult.Success(subject.asExternalModel())
            } else {
                DataResult.Failure(
                    error = "Subject not found".asStringWrapper()
                )
            }
        }
    }

    override suspend fun upsert(value: SubjectEntity): DataResult<Unit> {
        subjectDao.upsert(value)
        return DataResult.Success(Unit)
    }

    override suspend fun delete(subjectId: Int): DataResult<Unit> {
        subjectDao.delete(
            subjectId = subjectId,
            deletionTimestamp = System.currentTimeMillis()
        )

        return DataResult.Success(Unit)
    }
}