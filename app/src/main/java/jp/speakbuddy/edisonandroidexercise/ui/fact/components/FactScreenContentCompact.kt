package jp.speakbuddy.edisonandroidexercise.ui.fact.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.ui.fact.CAT_IMAGE_URL
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState


@Composable
fun FactScreenContentCompact(uiState: FactUiState) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        CatImage(CAT_IMAGE_URL, modifier = Modifier.align(Alignment.CenterHorizontally))
        FactSection(uiState, modifier = Modifier.fillMaxWidth())
    }
}