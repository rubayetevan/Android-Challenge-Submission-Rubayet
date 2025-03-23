package jp.speakbuddy.edisonandroidexercise.data.fact.repository

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.core.ResultError
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource.FactLocalDataSource
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.FactRemoteDataSource
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class FactRepositoryTest {
    @Mock
    private lateinit var factRemoteDataSource: FactRemoteDataSource
    @Mock
    private lateinit var factLocalDataSource: FactLocalDataSource

    private lateinit var factRepository: FactRepository
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testDispatcher = StandardTestDispatcher()
        factRemoteDataSource = mock()
        factLocalDataSource = mock()
        factRepository = FactRepositoryImpl(factRemoteDataSource, factLocalDataSource)
    }

    @Test
    fun `getNewFact when remote success then save and return success`() = runTest(testDispatcher) {
        val factResponse = FactResponse("New Fact", 15)
        val successResult = Result.Success(factResponse)
        whenever(factRemoteDataSource.getNewFact()).thenReturn(flowOf(successResult))
        val result = factRepository.getNewFact().first()
        assert(result is Result.Success<FactResponse>)
        assert((result as Result.Success<FactResponse>).data == factResponse)
        verify(factLocalDataSource).saveFact(factResponse)
    }

    @Test
    fun `getNewFact when remote success then save and return Error`() = runTest(testDispatcher) {
        val errorCode = 404
        val errorResult = Result.Error(ResultError(errorCode, "Not Found"))
        whenever(factRemoteDataSource.getNewFact()).thenReturn(flowOf(errorResult))
        val result = factRepository.getNewFact().first()
        assert(result is Result.Error)
        assert((result as Result.Error).code == errorCode)
    }

    @Test
    fun `getLastFact returns last fact from local data source`() = runTest(testDispatcher) {
        val factResponse = FactResponse("Last Fact", 20)
        whenever(factLocalDataSource.getLastFact()).thenReturn(flowOf(factResponse))
        val result = factRepository.getLastFact()
        assert(result == factResponse)
    }

    @Test
    fun `getLastFact returns last fact from local data source as null`() = runTest(testDispatcher) {
        whenever(factLocalDataSource.getLastFact()).thenReturn(flowOf(null))
        val result = factRepository.getLastFact()
        assert(result == null)
    }
}