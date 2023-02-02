package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail

@Composable
fun DetailSimilarPokemonItemSection(
    modifier: Modifier = Modifier,
    data: PokemonDetail,
    onSelectedPokemon: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onSelectedPokemon.invoke(data.pokemonId)
            }, elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(150.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.pokemonImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.placeholder_image),
                contentDescription = stringResource(R.string.pokemon_image),
            )

            Text(
                modifier = Modifier.padding(8.dp),
                text = data.pokemonName
            )
        }
    }
}