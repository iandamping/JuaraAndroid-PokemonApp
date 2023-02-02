package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail

@Composable
fun DetailPokemonSpriteImageSection(modifier: Modifier = Modifier, pokemonItem: PokemonDetail) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .weight(1f),
            model = ImageRequest.Builder(LocalContext.current).data(pokemonItem.pokemonSmallImage1)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.ic_no_data),
            contentDescription = "pokemon sprite 1"
        )

        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .weight(1f),
            model = ImageRequest.Builder(LocalContext.current).data(pokemonItem.pokemonSmallImage2)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.ic_no_data),
            contentDescription = "pokemon sprite 2"
        )

        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .weight(1f),
            model = ImageRequest.Builder(LocalContext.current).data(pokemonItem.pokemonSmallImage3)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.ic_no_data),
            contentDescription = "pokemon sprite 3"
        )

        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .weight(1f),
            model = ImageRequest.Builder(LocalContext.current).data(pokemonItem.pokemonSmallImage4)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.ic_no_data),
            contentDescription = "pokemon sprite 4"
        )

    }
}