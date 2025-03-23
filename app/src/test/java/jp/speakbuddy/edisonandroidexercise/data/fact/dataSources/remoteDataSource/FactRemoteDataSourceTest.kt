package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.network.FactRetrofitService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response


class FactRemoteDataSourceTest {
    @Mock
    private lateinit var catFactRetrofitService: FactRetrofitService
    private lateinit var factRemoteDataSource: FactRemoteDataSource
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        catFactRetrofitService = mock()
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)
        factRemoteDataSource = FactRemoteDataSourceImpl(catFactRetrofitService, testScope)
    }

    @Test
    fun getFactSuccessResponseTest() = runTest(testDispatcher) {
        val mockFactResponse = FactResponse(
            fact = "Cats have individual preferences for scratching surfaces and angles. Some are horizontal scratchers while others exercise their claws vertically.",
            length = 145,
        )
        val res: Response<FactResponse> = Response.success(mockFactResponse)
        whenever(catFactRetrofitService.getFact()).thenReturn(res)
        val resultFlow = factRemoteDataSource.getNewFact()
        assertEquals(Result.Loading, resultFlow.first())
        val successResult = resultFlow.drop(1).first()
        assert(successResult is Result.Success<FactResponse>)
        assertEquals(mockFactResponse, (successResult as Result.Success<FactResponse>).data)
    }

    @Test
    fun getFactNullBodyResponseTest() = runTest(testDispatcher) {
        val res: Response<FactResponse> = Response.success(null)
        whenever(catFactRetrofitService.getFact()).thenReturn(res)
        val resultFlow = factRemoteDataSource.getNewFact()
        assertEquals(Result.Loading, resultFlow.first())
        val result = resultFlow.drop(1).first()
        assert(result is Result.Error)
        assertEquals("Response body is null", (result as Result.Error).message)
    }

    @Test
    fun getFactErrorResponseTest() = runTest(testDispatcher) {
        val errorCode = 404
        val errorMessage = "Not Found"
        val res: Response<FactResponse> = Response.error(
            errorCode,
            errorMessage.toResponseBody(null)
        )
        whenever(catFactRetrofitService.getFact()).thenReturn(res)
        val resultFlow = factRemoteDataSource.getNewFact()
        assertEquals(Result.Loading, resultFlow.first())
        val result = resultFlow.drop(1).first()
        assert(result is Result.Error)
        assertEquals(errorCode, (result as Result.Error).code)
    }

}