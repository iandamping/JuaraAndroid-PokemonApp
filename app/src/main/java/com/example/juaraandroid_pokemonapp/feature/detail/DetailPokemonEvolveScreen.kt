package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonSpeciesEvolutionState

@Composable
fun DetailPokemonEvolveScreen(
    modifier: Modifier = Modifier,
    data: DetailPokemonSpeciesEvolutionState,
    onSelectedPokemon: (Int) -> Unit
) {

    Column(modifier.padding(8.dp)) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = "Evolution line",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(), elevation = 8.dp
        ) {
            when {
                data.data != null -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelectedPokemon.invoke(data.data.pokemonId)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(150.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(data.data.pokemonImage)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.placeholder_image),
                            error = painterResource(id = R.drawable.ic_no_data),
                            contentDescription = stringResource(R.string.pokemon_image)
                        )

                        Text(
                            text = data.data.pokemonName,
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
                data.failedMessage.isNotEmpty() -> {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = data.failedMessage,
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

        }


    }


}