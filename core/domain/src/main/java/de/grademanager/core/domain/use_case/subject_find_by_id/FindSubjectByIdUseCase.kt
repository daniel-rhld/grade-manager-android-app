package de.grademanager.core.domain.use_case.subject_find_by_id

import de.grademanager.core.model.Subject
import kotlinx.coroutines.flow.Flow

interface FindSubjectByIdUseCase {

    suspend fun findSubjectById(id: Int): Subject?

    fun findSubjectByIdAsFlow(id: Int): Flow<Subject?>

}