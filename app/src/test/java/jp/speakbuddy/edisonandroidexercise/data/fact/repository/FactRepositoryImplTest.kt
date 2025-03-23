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
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FactRepositoryImplTest {

    @Mock
    private lateinit var factRemoteDataSource: FactRemoteDataSource

    @Mock
    private lateinit var factLocalDataSource: FactLocalDataSource

    private lateinit var factRepository: FactRepositoryImpl
    private lateinit var testDispatcher: TestDispatcher


    @Before
    fun setup() {
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
        Assert.assertEquals(successResult, result)
        verify(factLocalDataSource).saveFact(factResponse)
    }

    @Test
    fun `getNewFact when remote error then don't save and return error`() =
        runTest(testDispatcher) {
            val errorResult = Result.Error(ResultError(code = 400, message = "404 not found"))
            whenever(factRemoteDataSource.getNewFact()).thenReturn(flowOf(errorResult))
            val result = factRepository.getNewFact().first()
            Assert.assertEquals(errorResult, result)
            verify(factLocalDataSource, never()).saveFact(any())
        }

    @Test
    fun `getLastFact returns last fact from local data source`() = runTest(testDispatcher) {
        val factResponse = FactResponse("Last Fact", 20)
        whenever(factLocalDataSource.getLastFact()).thenReturn(flowOf(factResponse))
        val result = factRepository.getLastFact()
        Assert.assertEquals(factResponse, result)
    }

}