package jp.speakbuddy.edisonandroidexercise.ui.fact

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.core.ResultError
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.domain.fact.FactService
import jp.speakbuddy.edisonandroidexercise.presentation.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.presentation.fact.models.FactUiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class FactViewModelTest {

    @MockK
    lateinit var factService: FactService

    private lateinit var viewModel: FactViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = FactViewModel(factService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadLastFact should update uiState with last fact`() = runTest {
        val factResponse = FactResponse(fact = "Test Fact", length = 10)
        coEvery { factService.getLastFact() } returns factResponse
        viewModel.uiState.test {
            assertEquals(FactUiState(), awaitItem())
            viewModel.loadLastFact()
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(
                FactUiState(fact = factResponse.fact, factLength = factResponse.length), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateFact should update uiState with success result`() = runTest {
        val factResponse = FactResponse(fact = "Test Fact", length = 10)
        coEvery { factService.getLastFact() } returns factResponse
        val mockFactResponse = FactResponse(
            fact = "Cats have individual preferences for scratching surfaces and angles. Some are horizontal scratchers while others exercise their claws vertically.",
            length = 145,
        )
        every { factService.getNewFact() } returns flowOf(Result.Success(mockFactResponse))
        viewModel.uiState.test {
            assertEquals(FactUiState(), awaitItem())
            viewModel.updateFact()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = awaitItem()
            assertEquals(
                FactUiState(
                    fact = mockFactResponse.fact, factLength = mockFactResponse.length, isLoading = false
                ), result
            )
            assert(result.showFactLength)
            assert(result.isMultipleCats)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateFact should update uiState with loading result`() = runTest {
        val factResponse = FactResponse(fact = "Test Fact", length = 10)
        coEvery { factService.getLastFact() } returns factResponse
        every { factService.getNewFact() } returns flowOf(Result.Loading)
        viewModel.uiState.test {
            assertEquals(FactUiState(), awaitItem())
            viewModel.updateFact()
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(FactUiState(isLoading = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateFact should update uiState with error result and emit error message`() = runTest {
        val errorMessage = "Not Found"
        every { factService.getNewFact() } returns flowOf(
            Result.Error(
                ResultError(
                    404, errorMessage
                )
            )
        )
        viewModel.errorMessageFlow.test {
            viewModel.updateFact()
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(errorMessage, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateFact should call factService getNewFact`() = runTest {
        every { factService.getNewFact() } returns flowOf(Result.Loading)
        viewModel.updateFact()
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { factService.getNewFact() }
    }

    @Test
    fun `loadLastFact should call factService getLastFact`() = runTest {
        coEvery { factService.getLastFact() } returns null
        viewModel.loadLastFact()
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { factService.getLastFact() }
    }

}