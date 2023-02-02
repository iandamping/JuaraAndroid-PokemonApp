package com.example.juaraandroid_pokemonapp.feature.quiz

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.*
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.LOTTIE_QUIZ_CORRECT_LOADING
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.LOTTIE_QUIZ_WRONG_LOADING

@Composable
fun ItemQuizPokemonScreen(
    modifier: Modifier = Modifier,
    pokemonId: Int,
    pokemonImage: String,
    randomName: List<String>,
    pokemonName: String,
    onDetailScreenIsClicked: (Int) -> Unit,
) {
    val density = LocalDensity.current

    val correctAnswerComposition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            LOTTIE_QUIZ_CORRECT_LOADING
        )
    )
    val progressCorrectAnswer by animateLottieCompositionAsState(
        correctAnswerComposition,
        iterations = LottieConstants.IterateForever
    )

    val wrongAnswerComposition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            LOTTIE_QUIZ_WRONG_LOADING
        )
    )
    val progressWrongAnswer by animateLottieCompositionAsState(
        wrongAnswerComposition,
        iterations = LottieConstants.IterateForever
    )

    var isAnswered by rememberSaveable {
        mutableStateOf(false)
    }
    var isAnswerCorrect by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .width(width = 300.dp)
            .wrapContentHeight()
            .clickable(isAnswered) {
                onDetailScreenIsClicked.invoke(pokemonId)
            },
        elevation = 4.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (imageRef, logoRef, realPokemonNameRef, firstButtonRef, middleButtonRef, lastButtonRef, successAnimationRef) = createRefs()
            val imageGuideLine = createGuidelineFromTop(0.3f)
            val buttonGuideLine = createGuidelineFromBottom(0.3f)
            val realPokemonNameGuideline = createGuidelineFromBottom(0.2f)
            Image(
                modifier = Modifier.constrainAs(logoRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(imageGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                painter = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = stringResource(R.string.pokemon_logo)
            )

            AsyncImage(
                modifier = Modifier.constrainAs(imageRef) {
                    top.linkTo(imageGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttonGuideLine)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                model = ImageRequest.Builder(LocalContext.current).data(pokemonImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.pokemon_image),
                placeholder = painterResource(id = R.drawable.placeholder_image),
                error = painterResource(id = R.drawable.ic_no_data),
                colorFilter = if (!isAnswered) ColorFilter.tint(Color.Gray) else null
            )

            AnimatedVisibility(
                modifier = Modifier.constrainAs(successAnimationRef) {


                    top.linkTo(realPokemonNameGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints

                },
                visible = isAnswered,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()

            ) {

                LottieAnimation(
                    modifier = Modifier.size(if (isAnswerCorrect) 100.dp else 50.dp),
                    composition = if (isAnswerCorrect) correctAnswerComposition else wrongAnswerComposition,
                    progress = { if (isAnswerCorrect) progressCorrectAnswer else progressWrongAnswer },
                )


            }

            if (isAnswered) {
                Text(
                    modifier = Modifier.constrainAs(realPokemonNameRef) {
                        top.linkTo(imageRef.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        centerHorizontallyTo(parent)
                    },
                    text = pokemonName, style = MaterialTheme.typography.h4.copy(
                        fontFamily = LatoFontFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )
            } else {
                Button(
                    modifier = Modifier.constrainAs(firstButtonRef) {
                        top.linkTo(buttonGuideLine)
                        start.linkTo(parent.start, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                        bottom.linkTo(middleButtonRef.top)
                        width = Dimension.fillToConstraints
                    },
                    elevation = ButtonDefaults.elevation(8.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = !isAnswered,
                    onClick = {
                        isAnswered = !isAnswered
                        if (randomName.first() == pokemonName) {
                            isAnswerCorrect = true
                        }

                    }) {
                    Text(text = randomName.first())
                }
                Button(
                    modifier = Modifier.constrainAs(middleButtonRef) {
                        top.linkTo(firstButtonRef.bottom)
                        start.linkTo(parent.start, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                        bottom.linkTo(lastButtonRef.top)
                        width = Dimension.fillToConstraints
                    },
                    elevation = ButtonDefaults.elevation(8.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = !isAnswered,
                    onClick = {
                        isAnswered = !isAnswered
                        if (randomName[1] == pokemonName) {
                            isAnswerCorrect = true
                        }
                    }) {
                    Text(text = randomName[1])
                }
                Button(
                    modifier = Modifier.constrainAs(lastButtonRef) {
                        top.linkTo(middleButtonRef.bottom)
                        start.linkTo(parent.start, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                    elevation = ButtonDefaults.elevation(8.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = !isAnswered,
                    onClick = {
                        isAnswered = !isAnswered
                        if (randomName.last() == pokemonName) {
                            isAnswerCorrect = true
                        }
                    }) {
                    Text(text = randomName.last())
                }
            }


        }
    }
}