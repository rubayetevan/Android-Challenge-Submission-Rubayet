package jp.speakbuddy.edisonandroidexercise.domain.fact

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.core.ResultError
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.fact.repository.FactRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class FactServiceImplTest {

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var factService: FactServiceImpl

    @Mock
    private lateinit var factRepository: FactRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testDispatcher = StandardTestDispatcher()
        factRepository = mock()
        factService = FactServiceImpl(factRepository)
    }

    @Test
    fun `getNewFact calls repository getNewFact and returns Success`() = runTest(testDispatcher) {

        val factResponse = FactResponse("New Fact", 15)
        val expectedResult = Result.Success(factResponse)
        whenever(factRepository.getNewFact()).thenReturn(flowOf(expectedResult))
        val result = factService.getNewFact().first()
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getNewFact calls repository getNewFact and returns Error`() = runTest(testDispatcher) {
        val expectedError = Result.Error(ResultError(code = -4, message = "An error occurred"))
        whenever(factRepository.getNewFact()).thenReturn(flowOf(expectedError))
        val result = factService.getNewFact().first()
        assertEquals(expectedError, result)
    }
    @Test
    fun `getNewFact calls repository getNewFact and returns Loading`() = runTest(testDispatcher) {
        val expectedLoading = Result.Loading
        whenever(factRepository.getNewFact()).thenReturn(flowOf(expectedLoading))
        val result = factService.getNewFact().first()
        assertEquals(expectedLoading, result)
    }

    @Test
    fun `getLastFact calls repository getLastFact and returns a FactResponse`() = runTest(testDispatcher) {
        val factResponse = FactResponse("Last Fact", 20)
        whenever(factRepository.getLastFact()).thenReturn(factResponse)
        val result = factService.getLastFact()
        assertEquals(factResponse, result)
    }

    @Test
    fun `getLastFact calls repository getLastFact and returns null`() = runTest(testDispatcher) {
        whenever(factRepository.getLastFact()).thenReturn(null)
        val result = factService.getLastFact()
        assertEquals(null, result)
    }

}