package jp.speakbuddy.edisonandroidexercise.ui.fact.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState

@Composable
fun FactSection(uiState: FactUiState, modifier: Modifier = Modifier) {
    if (uiState.fact != null) {
        FactCard(uiState, modifier = modifier)
    } else {
        EmptyFactSection()
    }
}