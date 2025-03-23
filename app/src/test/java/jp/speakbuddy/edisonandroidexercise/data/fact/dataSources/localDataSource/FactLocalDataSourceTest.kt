package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource

import androidx.datastore.core.DataStore
import jp.speakbuddy.edisonandroidexercise.FactProto
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class FactLocalDataSourceTest {
    @Mock
    private lateinit var dataStore: DataStore<FactProto>
    private lateinit var factLocalDataSource: FactLocalDataSource

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataStore = mock()
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)
        factLocalDataSource = FactLocalDataSourceImpl(dataStore,testScope)
    }

    @Test
    fun `getLastFact returns FactResponse when dataStore has data`() = runTest(testDispatcher) {
        val fact = "Test Fact"
        val length = 10
        val factProto = FactProto.newBuilder().setFact(fact).setLength(length).build()
        val expectedFactResponse = FactResponse(fact, length)
        whenever(dataStore.data).thenReturn(flowOf(factProto))
        val factResponse = factLocalDataSource.getLastFact().first()
        assertEquals(expectedFactResponse, factResponse)

    }

    @Test
    fun `getLastFact returns FactResponse when dataStore has no data`() = runTest(testDispatcher) {
        val factProto = FactProto.getDefaultInstance()
        whenever(dataStore.data).thenReturn(flowOf(factProto))
        val factResponse = factLocalDataSource.getLastFact().first()
        assertEquals(null, factResponse)
    }

}