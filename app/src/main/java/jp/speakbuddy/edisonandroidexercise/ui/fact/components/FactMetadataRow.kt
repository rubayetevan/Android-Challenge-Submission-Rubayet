package jp.speakbuddy.edisonandroidexercise.ui.fact.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState

@Composable
fun FactMetadataRow(uiState: FactUiState) {
    if (uiState.isMultipleCats || uiState.showFactLength) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MetadataChip(
                visible = uiState.isMultipleCats,
                text = stringResource(R.string.multiple_cats),
                backgroundColor = Color(0xFFffd54f)
            )
            MetadataChip(
                visible = uiState.showFactLength,
                text = stringResource(R.string.fact_length, uiState.factLength),
                backgroundColor = Color(0xFFF0F0F0)
            )
        }
    }
}