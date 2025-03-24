package jp.speakbuddy.edisonandroidexercise.presentation.fact.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MetadataChip(visible: Boolean, text: String, backgroundColor: Color) {
    AnimatedVisibility(visible = visible) {
        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}
