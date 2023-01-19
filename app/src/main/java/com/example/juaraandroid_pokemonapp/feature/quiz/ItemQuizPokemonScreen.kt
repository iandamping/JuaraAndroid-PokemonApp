package com.example.juaraandroid_pokemonapp.feature.quiz

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily

@Composable
fun ItemQuizPokemonScreen(
    modifier: Modifier = Modifier,
    pokemonImage: String,
    randomName: List<String>,
    pokemonName: String
) {

    var isAnswered by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .width(width = 300.dp)
            .wrapContentHeight(),
        elevation = 4.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (imageRef, logoRef, realPokemonNameRef, firstButtonRef, middleButtonRef, lastButtonRef) = createRefs()
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
                contentDescription = "pokemon logo"
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
                contentDescription = "pokemon image",
                placeholder = painterResource(id = R.drawable.placeholder_image),
                colorFilter = if (!isAnswered) ColorFilter.tint(Color.Gray) else null
            )

            if (isAnswered) {
                Text(
                    modifier = Modifier.constrainAs(realPokemonNameRef) {
                        top.linkTo(realPokemonNameGuideline)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
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
                    }) {
                    Text(text = randomName[0])
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
                    }) {
                    Text(text = randomName[2])
                }
            }


        }
    }
}