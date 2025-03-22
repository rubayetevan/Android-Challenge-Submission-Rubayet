package jp.speakbuddy.edisonandroidexercise.ui.fact.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import jp.speakbuddy.edisonandroidexercise.R

@Composable
fun CatImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.cat_image_description),
    )
}
