package jp.speakbuddy.edisonandroidexercise.presentation.fact

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowWidthSizeClass
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.presentation.fact.components.FactScreenContentExpanded
import jp.speakbuddy.edisonandroidexercise.presentation.fact.components.FactScreenContentCompact
import jp.speakbuddy.edisonandroidexercise.presentation.fact.components.RefreshFactButton
import jp.speakbuddy.edisonandroidexercise.presentation.fact.models.FactUiState

const val CAT_IMAGE_URL =
    "https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg"


@Composable
fun FactScreen(
    viewModel: FactViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessageFlow.collectAsStateWithLifecycle(null)
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
    FactScreenContent(uiState, viewModel::updateFact, isCompact)
    errorMessage?.let {errMsg ->
        Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactScreenContent(uiState: FactUiState, updateFact: () -> Unit, isCompact: Boolean) {
    Scaffold(
        containerColor = Color(0xFFF5FAFE),
        floatingActionButton = {
            RefreshFactButton(onClick = updateFact, enabled = !uiState.isLoading)
        },
        topBar = {
            CenterAlignedTopAppBar(
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
            if (isCompact) {
                FactScreenContentCompact(uiState)
            } else {
                FactScreenContentExpanded(uiState)
            }
        }
    }
}


@Preview(uiMode = Configuration.ORIENTATION_PORTRAIT)
@Composable
fun FactScreenContentPreview() {
    val uiState = FactUiState(
        fact = "Cats have individual preferences for scratching surfaces and angles. Some are horizontal scratchers while others exercise their claws vertically.",
        factLength = 145,
        isLoading = false
    )
    FactScreenContent(uiState, updateFact = {}, isCompact = true)
}
