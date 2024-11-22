package de.grademanger.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import de.grademanger.core.datastore.model.grade_ordering.GradeOrderingDataStore
import de.grademanger.core.datastore.model.user.UserDataStore
import de.grademanger.core.datastore.serializer.GradeOrderingDataStoreSerializer
import de.grademanger.core.datastore.serializer.UserDataStoreSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

const val DATASTORE_QUALIFIER_GRADE_ORDERING = "GradeOrdering"
const val DATASTORE_QUALIFIER_USER = "User"

val DataStoreModule = module {
    single<DataStore<GradeOrderingDataStore>>(
        qualifier = StringQualifier(value = DATASTORE_QUALIFIER_GRADE_ORDERING)
    ) {
        DataStoreFactory.create(
            serializer = GradeOrderingDataStoreSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler {
                GradeOrderingDataStoreSerializer.defaultValue
            },
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = {
                androidContext().dataStoreFile("grade-ordering-datastore.json")
            }
        )
    }

    single<DataStore<UserDataStore>>(
        qualifier = StringQualifier(value = DATASTORE_QUALIFIER_USER)
    ) {
        DataStoreFactory.create(
            serializer = UserDataStoreSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler {
                UserDataStoreSerializer.defaultValue
            },
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = {
                androidContext().dataStoreFile("user-datastore.json")
            }
        )
    }
}