package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.FactProto
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

private const val FACT_PROTOBUF_FILE_NAME = "fact.pb"

@Module
@InstallIn(SingletonComponent::class)
object FactDataStoreModule {
    @Provides
    @Singleton
    fun provideFactDataStore(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ): DataStore<FactProto> {
        return DataStoreFactory.create(
            serializer = FactSerializer,
            produceFile = { context.filesDir.resolve(FACT_PROTOBUF_FILE_NAME) },
            corruptionHandler = ReplaceFileCorruptionHandler {
                FactProto.getDefaultInstance()
            },
            scope = coroutineScope
        )
    }
}