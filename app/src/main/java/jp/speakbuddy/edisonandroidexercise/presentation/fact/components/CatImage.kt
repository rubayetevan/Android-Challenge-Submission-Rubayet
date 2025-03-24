package jp.speakbuddy.edisonandroidexercise.presentation.fact.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import jp.speakbuddy.edisonandroidexercise.R

@Composable
fun CatImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.cat_image_description),
        contentScale = ContentScale.Crop,
        modifier = modifier.clip(CircleShape).size(150.dp),
    )
}
