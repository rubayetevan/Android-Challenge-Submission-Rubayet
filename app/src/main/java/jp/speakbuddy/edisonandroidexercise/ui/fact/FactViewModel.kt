package jp.speakbuddy.edisonandroidexercise.ui.fact


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor() : ViewModel() {
    fun updateFact(completion: () -> Unit): String =
        runBlocking {
            try {
                " no fact available tight now"
            } catch (e: Throwable) {
                "something went wrong. error = ${e.message}"
            }.also { completion() }
        }
}
