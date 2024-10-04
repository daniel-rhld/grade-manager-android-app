package de.grademanager.feature.subjects.data.repository

import de.grademanager.core.data.database.GradeManagerDatabase
import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.data.model.entity.mapToDomainObject
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity
import de.grademanager.feature.subjects.data.model.entity.mapToDomainObject
import de.grademanager.feature.subjects.data.model.view.mapToDomainObject
import de.grademanager.feature.subjects.domain.models.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepositoryImpl(
    private val database: GradeManagerDatabase
) : SubjectRepository {
    override fun getAllExistingSubjectsOrdered(
        orderColumn: String,
        orderAscending: Boolean
    ): Flow<List<Subject>> {
        return database.getSubjectDao().getAllExistingSubjectsWithGradesOrdered(
            orderColumn = orderColumn,
            orderAscending = orderAscending
        ).map { list ->
            list.map { subjectWithGrades ->
                subjectWithGrades.self.mapToDomainObject(
                    grades = subjectWithGrades.grades.map(GradeEntity::mapToDomainObject)
                )
            }
        }
    }

    override suspend fun findById(id: Int): DataResult<Subject> {
        return database.getSubjectDao().findById(id = id).let { subject ->
            if (subject != null) {
                DataResult.Success(subject.mapToDomainObject())
            } else {
                // TODO: Set error message
                DataResult.Failure(error = null)
            }
        }
    }

    override suspend fun upsert(value: SubjectEntity): DataResult<Unit> {
        database.getSubjectDao().upsert(value)
        return DataResult.Success(Unit)
    }

    override suspend fun delete(subjectId: Int): DataResult<Unit> {
        database.getSubjectDao().delete(
            subjectId = subjectId,
            deletionTimestamp = System.currentTimeMillis()
        )

        return DataResult.Success(Unit)
    }
}