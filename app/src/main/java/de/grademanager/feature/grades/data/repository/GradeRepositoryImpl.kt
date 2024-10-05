package de.grademanager.feature.grades.data.repository

import de.grademanager.core.data.database.GradeManagerDatabase
import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.data.model.entity.mapToDomainObject
import de.grademanager.feature.grades.domain.models.Grade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GradeRepositoryImpl(
    private val database: GradeManagerDatabase
) : GradeRepository {

    override fun getAllGradesForSubject(subjectId: Int): Flow<List<Grade>> {
        return database.getGradeDao()
            .getAllGradesForSubject(subjectId).map { list ->
                list.map(GradeEntity::mapToDomainObject)
            }
    }

    override suspend fun upsert(entity: GradeEntity): DataResult<Unit> {
        database.getGradeDao().upsert(entity)

        return DataResult.Success(Unit)
    }

    override suspend fun deleteById(id: Int): DataResult<Unit> {
        database.getGradeDao().deleteById(
            id = id,
            deletionTimestamp = System.currentTimeMillis()
        )

        return DataResult.Success(Unit)
    }

}