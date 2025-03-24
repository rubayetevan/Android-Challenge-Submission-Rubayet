package jp.speakbuddy.edisonandroidexercise.presentation.fact.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import jp.speakbuddy.edisonandroidexercise.R

@Composable
fun RefreshFactButton(onClick: () -> Unit, enabled: Boolean) {
    Button(
        onClick = onClick,
        enabled = enabled,
    ) {
        Text(text = stringResource(R.string.update_fact))
    }
}