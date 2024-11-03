package de.grademanger.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import de.grademanger.core.datastore.model.GradeOrderingDataStore
import de.grademanger.core.datastore.serializer.GradeOrderingDataStoreSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataStoreModule = module {
    single<DataStore<GradeOrderingDataStore>> {
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
}