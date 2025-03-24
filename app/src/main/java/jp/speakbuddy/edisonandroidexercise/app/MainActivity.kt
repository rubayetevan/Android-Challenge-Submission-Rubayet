package jp.speakbuddy.edisonandroidexercise.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import jp.speakbuddy.edisonandroidexercise.presentation.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.presentation.theme.EdisonAndroidExerciseTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdisonAndroidExerciseTheme {
                FactScreen()
            }
        }
    }
}