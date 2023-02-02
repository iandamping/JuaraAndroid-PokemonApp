package com.example.juaraandroid_pokemonapp.feature.quiz.quizDialog

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.LOTTIE_QUIZ_INTRO_LOADING

@Composable
fun QuizIntroDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean,
    onDismissDialogRequest: (Boolean) -> Unit
) {
    if (dialogState) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Asset(
                LOTTIE_QUIZ_INTRO_LOADING
            )
        )
        val progress by animateLottieCompositionAsState(composition)

        if (progress == 1.0f) {
            onDismissDialogRequest.invoke(false)
        }
        AlertDialog(
            modifier = modifier,
            backgroundColor = Color.Transparent,
            onDismissRequest = { onDismissDialogRequest.invoke(false) },
            title = null,
            text = null,
            shape = RoundedCornerShape(8.dp),
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
            buttons = {
                LottieAnimation(
                    modifier = Modifier.size(150.dp),
                    composition = composition,
                    progress = { progress },
                )
            }
        )
    }

}