package de.grademanager.core.data.repository.grade

import androidx.datastore.core.DataStore
import de.grademanager.core.data.util.DataResult
import de.grademanager.core.database.dao.GradeDao
import de.grademanager.core.database.model.grade.GradeEntity
import de.grademanager.core.database.model.grade.asExternalModel
import de.grademanager.core.model.Grade
import de.grademanager.core.model.GradeOrdering
import de.grademanger.core.datastore.model.GradeOrderingDataStore
import de.grademanger.core.datastore.model.asDataStore
import de.grademanger.core.datastore.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GradeRepositoryImpl(
    private val gradeDao: GradeDao,
    private val gradeOrderingSettingsDataStore: DataStore<GradeOrderingDataStore>
) : GradeRepository {

    override fun getAllGradesForSubject(subjectId: Int): Flow<List<Grade>> {
        return gradeDao.getAllGradesForSubject(subjectId = subjectId).map { list ->
            list.map(GradeEntity::asExternalModel)
        }
    }

    override suspend fun upsert(entity: GradeEntity): DataResult<Unit> {
        gradeDao.upsert(entity)
        return DataResult.Success(Unit)
    }

    override suspend fun deleteById(id: Int): DataResult<Unit> {
        gradeDao.deleteById(
            id = id,
            deletionTimestamp = System.currentTimeMillis()
        )
        return DataResult.Success(Unit)
    }

    override suspend fun restoreById(id: Int): DataResult<Unit> {
        gradeDao.restoreById(id = id)
        return DataResult.Success(Unit)
    }

    override fun getGradeOrderingSettings(): Flow<GradeOrdering> {
        return gradeOrderingSettingsDataStore.data.map(GradeOrderingDataStore::asExternalModel)
    }

    override suspend fun updateGradeOrdering(value: GradeOrdering) {
        gradeOrderingSettingsDataStore.updateData {
            value.asDataStore()
        }
    }

}