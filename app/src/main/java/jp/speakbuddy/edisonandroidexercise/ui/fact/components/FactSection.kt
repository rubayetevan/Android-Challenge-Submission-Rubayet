package jp.speakbuddy.edisonandroidexercise.ui.fact.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState

@Composable
fun FactSection(uiState: FactUiState) {
    Text(
        text = stringResource(R.string.fact),
        style = MaterialTheme.typography.titleLarge,
    )
    uiState.fact?.let { fact ->
        FactCard(fact, uiState)
    }
}