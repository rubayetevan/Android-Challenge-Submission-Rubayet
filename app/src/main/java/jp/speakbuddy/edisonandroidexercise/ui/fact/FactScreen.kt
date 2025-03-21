package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.fact.models.FactUiState

private const val CAT_IMAGE_URL =
    "https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactScreen(
    viewModel: FactViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF5FAFE),
        floatingActionButton = {
            RefreshFactButton(onClick = viewModel::updateFact, enabled = !uiState.isLoading)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cat_facts),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            FactScreenContent(uiState)
        }
    }
}


@Composable
fun FactScreenContent(uiState: FactUiState) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        CatImage(CAT_IMAGE_URL)
        FactSection(uiState)
    }
}

@Composable
private fun RefreshFactButton(onClick: () -> Unit, enabled: Boolean) {
    Button(
        onClick = onClick,
        enabled = enabled,
    ) {
        Text(text = stringResource(R.string.update_fact))
    }
}

@Composable
private fun CatImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.cat_image_description),
    )
}

@Composable
private fun FactSection(uiState: FactUiState) {
    Text(
        text = stringResource(R.string.fact),
        style = MaterialTheme.typography.titleLarge,
    )
    uiState.fact?.let { fact ->
        FactCard(fact, uiState)
    }
}

@Composable
private fun FactCard(fact: String, uiState: FactUiState) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = fact, style = MaterialTheme.typography.bodyLarge)
            FactMetadataRow(uiState)
        }
    }
}

@Composable
private fun FactMetadataRow(uiState: FactUiState) {
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

@Composable
private fun MetadataChip(visible: Boolean, text: String, backgroundColor: Color) {
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