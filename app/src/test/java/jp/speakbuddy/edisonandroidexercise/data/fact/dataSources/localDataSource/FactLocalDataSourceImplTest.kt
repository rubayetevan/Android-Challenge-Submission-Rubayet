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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class FactLocalDataSourceImplTest {

    @Mock
    private lateinit var dataStoreMock: DataStore<FactProto>
    private lateinit var factLocalDataSource: FactLocalDataSourceImpl
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope


    @Before
    fun setup() {
        dataStoreMock = mock()
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)
        factLocalDataSource = FactLocalDataSourceImpl(dataStoreMock, testScope)
    }


    @Test
    fun `getLastFact returns FactResponse when dataStore has data`() = runTest(testDispatcher) {
        val fact = "Test Fact"
        val length = 10
        val factProto = FactProto.newBuilder().setFact(fact).setLength(length).build()
        val expectedFactResponse = FactResponse(fact, length)
        whenever(dataStoreMock.data).thenReturn(flowOf(factProto))
        val result: FactResponse? = factLocalDataSource.getLastFact().first()
        assertEquals(expectedFactResponse, result)
    }

    @Test
    fun `getLastFact returns null when dataStore has no data`() = runTest(testDispatcher) {
        whenever(dataStoreMock.data).thenReturn(flowOf(FactProto.getDefaultInstance()))
        val result: FactResponse? = factLocalDataSource.getLastFact().first()
        assertEquals(null, result)
    }

}