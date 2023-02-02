package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    isGridOrList: Boolean,
    data: PokemonDetail,
    onSelectedPokemon: (Int) -> Unit
) {

    Card(
        modifier = modifier
            .size(
                if (isGridOrList) 200.dp else 350.dp
            )
            .clickable { onSelectedPokemon(data.pokemonId) },
        elevation = 4.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (titleRef, imageRef) = createRefs()
            val logoGuideLine = createGuidelineFromTop(0.2f)

            Text(
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(logoGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                text = data.pokemonName, style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = LatoFontFamily,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
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
                placeholder = painterResource(id = R.drawable.placeholder_image),
                contentDescription = stringResource(R.string.pokemon_image)
            )
        }

    }

}