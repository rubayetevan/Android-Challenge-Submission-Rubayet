package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme

private const val CAT_IMAGE_URL =
    "https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactScreen(
    viewModel: FactViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    Scaffold(
        containerColor = Color(0xFFF5FAFE),
        contentColor = Color(0xFFF5FAFE),
        floatingActionButton = {
            Button(
                onClick = { viewModel.updateFact() },
                enabled = !uiState.isLoading,
            ) {
                Text(text = "Update fact")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cat Facts", style = MaterialTheme.typography.headlineLarge)
                })
        }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                AsyncImage(
                    model = CAT_IMAGE_URL,
                    contentDescription = "Translated description of what the image contains",
                )

                Text(
                    text = "Fact",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 180.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    uiState.fact?.let { fact ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = fact, style = MaterialTheme.typography.bodyLarge
                                )
                                if(uiState.isMultipleCats || uiState.showFactLength) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    ) {
                                        AnimatedVisibility(visible = uiState.isMultipleCats) {
                                            Box(
                                                modifier = Modifier.background(
                                                    color = Color(0xFFffd54f),
                                                    shape = RoundedCornerShape(15.dp)
                                                )
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(
                                                        vertical = 5.dp, horizontal = 10.dp
                                                    ),
                                                    text = "Multiple cats!",
                                                    style = MaterialTheme.typography.labelLarge,
                                                )
                                            }
                                        }
                                        AnimatedVisibility(uiState.showFactLength) {
                                            Box(
                                                modifier = Modifier.background(
                                                    color = Color(0xFFF0F0F0),
                                                    shape = RoundedCornerShape(15.dp)
                                                )
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(
                                                        vertical = 5.dp, horizontal = 10.dp
                                                    ),
                                                    text = "Length: ${uiState.factLength}",
                                                    style = MaterialTheme.typography.labelLarge,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen()
    }
}
