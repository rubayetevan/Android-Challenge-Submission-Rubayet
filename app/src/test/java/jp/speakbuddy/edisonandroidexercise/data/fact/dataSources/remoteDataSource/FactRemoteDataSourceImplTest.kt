package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.core.transformToResult
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.network.FactRetrofitService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class FactRemoteDataSourceImplTest {

    @Mock
    private lateinit var mockFactRetrofitService: FactRetrofitService

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)
    }

    @Test
    fun `getNewFact returns success result`() = runTest(testDispatcher) {
        val mockFactResponse = FactResponse(
            fact = "Cats have individual preferences for scratching surfaces and angles. Some are horizontal scratchers while others exercise their claws vertically.",
            length = 145,
        )
        `when`(mockFactRetrofitService.getFact()).thenReturn(Response.success(mockFactResponse))

        testScope.launch {
            transformToResult(testScope.coroutineContext) {
                mockFactRetrofitService.getFact()
            }.collect { result ->
                when (result) {
                    is Result.Error -> {
                        assertTrue(false)
                    }

                    is Result.Loading -> {}
                    is Result.Success<FactResponse> -> {
                        assertEquals(mockFactResponse, result.data)
                    }
                }
            }
        }
    }

    @Test
    fun `getNewFact returns 404 error result`() = runTest(testDispatcher) {
        val errorMessage = "Error"
        val errorCode = 404
        `when`(mockFactRetrofitService.getFact()).thenReturn(
            Response.error(
                errorCode,
                errorMessage.toResponseBody(null)
            )
        )

        testScope.launch {
            transformToResult(testScope.coroutineContext) {
                mockFactRetrofitService.getFact()
            }.collect { result ->
                when (result) {
                    is Result.Error -> {
                        assertTrue(result.code == errorCode && result.message == errorMessage)
                    }

                    is Result.Loading -> {}
                    is Result.Success<FactResponse> -> {
                        assertTrue(false)
                    }
                }
            }
        }
    }

    @Test
    fun `getNewFact returns emptyBody error result`() = runTest(testDispatcher) {
        val message = "Response body is null"
        val code = 200
        `when`(mockFactRetrofitService.getFact()).thenReturn(
            Response.success<FactResponse>(
                code,
                null
            )
        )

        testScope.launch {
            transformToResult(testScope.coroutineContext) {
                mockFactRetrofitService.getFact()
            }.collect { result ->
                when (result) {
                    is Result.Error -> {
                        assertTrue(result.code == code && result.message == message)
                    }

                    is Result.Loading -> {}
                    is Result.Success<FactResponse> -> {
                        assertTrue(false)
                    }
                }
            }
        }
    }

}