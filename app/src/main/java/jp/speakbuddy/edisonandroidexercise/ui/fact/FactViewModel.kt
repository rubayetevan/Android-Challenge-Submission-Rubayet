package jp.speakbuddy.edisonandroidexercise.ui.fact


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.domain.fact.FactService
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(private val factService: FactService) : ViewModel() {

    private val _uiState = MutableStateFlow(FactUiState())
    val uiState: StateFlow<FactUiState> = _uiState.onStart {
        updateFact()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = FactUiState()
    )

    fun updateFact() {
        viewModelScope.launch {
            factService.getNewFact().collect { result ->
                when (result) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Result.Success<FactResponse> -> {
                        _uiState.update {
                            it.copy(
                                fact = result.data.fact,
                                factLength = result.data.length,
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }
}
