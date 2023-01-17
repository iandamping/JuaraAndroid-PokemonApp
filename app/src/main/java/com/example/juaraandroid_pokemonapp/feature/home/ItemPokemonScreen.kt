package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily

@Composable
fun ItemPokemonScreen(
    modifier: Modifier = Modifier,
    data: PokemonDetail,
    onSelectedPokemon: (Int) -> Unit
) {

    Card(
        modifier = modifier
            .size(200.dp)
            .clickable { onSelectedPokemon(data.pokemonId) },
        elevation = 4.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (titleRef, imageRef, logoRef) = createRefs()
            val logoGuideLine = createGuidelineFromTop(0.2f)
            Image(
                modifier = Modifier.constrainAs(logoRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(logoGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                painter = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = "pokemon logo"
            )

            AsyncImage(
                modifier = Modifier.constrainAs(imageRef) {
                    top.linkTo(logoGuideLine, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                model = ImageRequest.Builder(LocalContext.current).data(data.pokemonImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "pokemon image"
            )

//            Text(
//                modifier = Modifier.constrainAs(titleRef) {
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                },
//                text = data.pokemonName, style = MaterialTheme.typography.subtitle1.copy(
//                    fontFamily = LatoFontFamily, textAlign = TextAlign.Center
//                )
//            )
        }

    }

}