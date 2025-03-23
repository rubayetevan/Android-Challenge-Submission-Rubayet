package jp.speakbuddy.edisonandroidexercise.ui.fact.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.fact.CAT_IMAGE_URL
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState


@Composable
fun FactScreenContentExpanded(uiState: FactUiState) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 56.dp, end = 156.dp, top = 16.dp, bottom = 56.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        CatImage(CAT_IMAGE_URL)
        FactSection(uiState, modifier = Modifier.fillMaxWidth())
    }
}