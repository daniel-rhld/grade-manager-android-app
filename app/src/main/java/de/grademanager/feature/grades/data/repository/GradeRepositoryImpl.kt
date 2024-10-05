package de.grademanager.feature.grades.data.repository

import androidx.datastore.core.DataStore
import de.grademanager.core.data.database.GradeManagerDatabase
import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.data.model.entity.mapToDomainObject
import de.grademanager.feature.grades.data.model.local_settings.GradeOrderingSettingsLocal
import de.grademanager.feature.grades.data.model.local_settings.mapToDomainObject
import de.grademanager.feature.grades.domain.models.Grade
import de.grademanager.feature.subjects.domain.models.GradeOrdering
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GradeRepositoryImpl(
    private val database: GradeManagerDatabase,
    private val gradeOrderingSettingsDataStore: DataStore<GradeOrderingSettingsLocal>
) : GradeRepository {

    override fun getAllGradesForSubject(subjectId: Int): Flow<List<Grade>> {
        return database.getGradeDao()
            .getAllGradesForSubject(subjectId = subjectId).map { list ->
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

    override suspend fun restoreById(id: Int): DataResult<Unit> {
        database.getGradeDao().restoreById(id = id)

        return DataResult.Success(Unit)
    }

    override fun getGradeOrderingSettings(): Flow<GradeOrdering> {
        return gradeOrderingSettingsDataStore.data.map { it.mapToDomainObject() }
    }

    override suspend fun updateGradeOrdering(value: GradeOrdering) {
        gradeOrderingSettingsDataStore.updateData {
            when (value) {
                is GradeOrdering.ReceivedAt -> GradeOrderingSettingsLocal.ReceivedAt(value.ascending)
                is GradeOrdering.Value -> GradeOrderingSettingsLocal.Value(value.ascending)
            }
        }
    }

}