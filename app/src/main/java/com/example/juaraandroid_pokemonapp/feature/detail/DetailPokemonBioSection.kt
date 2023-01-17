package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail

@Composable
fun DetailPokemonBioSection(modifier: Modifier = Modifier, pokemonItem: PokemonDetail) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Name",
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonName,
                modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(id = R.string.title_weight),
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = stringResource(
                    id = R.string.pokemon_weight,
                    pokemonItem.pokemonWeight.toString()
                ),
                modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(id = R.string.title_height),
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = stringResource(
                    id = R.string.pokemon_height,
                    pokemonItem.pokemonHeight.toString()
                ),
                modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }
    }
}