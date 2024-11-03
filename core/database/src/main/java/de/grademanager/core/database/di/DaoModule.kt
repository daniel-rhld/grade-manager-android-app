package de.grademanager.core.database.di

import de.grademanager.core.database.GradeManagerDatabase
import de.grademanager.core.database.dao.GradeDao
import de.grademanager.core.database.dao.SubjectDao
import org.koin.dsl.module

val DaoModule = module {
    single<GradeDao> {
        get<GradeManagerDatabase>().getGradeDao()
    }

    single<SubjectDao> {
        get<GradeManagerDatabase>().getSubjectDao()
    }
}