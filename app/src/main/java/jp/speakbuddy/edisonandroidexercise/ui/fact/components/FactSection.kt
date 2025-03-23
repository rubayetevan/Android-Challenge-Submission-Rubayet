package jp.speakbuddy.edisonandroidexercise.ui.fact.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState

@Composable
fun FactSection(uiState: FactUiState, modifier: Modifier = Modifier) {
    uiState.fact?.let { fact ->
        FactCard(fact, uiState, modifier = modifier)
    }
}