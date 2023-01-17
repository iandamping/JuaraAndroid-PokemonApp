package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail

@Composable
fun DetailPokemonStatSection(modifier: Modifier = Modifier, pokemonItem: PokemonDetail) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = pokemonItem.pokemonStat0.name,
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonStat0.point.toString(),
                modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }

         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = pokemonItem.pokemonStat1.name,
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonStat1.point.toString(),
                modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }

         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = pokemonItem.pokemonStat2.name,
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonStat2.point.toString(),
                modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }

         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = pokemonItem.pokemonStat3.name,
                modifier = modifier

                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonStat3.point.toString(),
                modifier = modifier

                    .wrapContentWidth(Alignment.End)
            )
        }

         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = pokemonItem.pokemonStat4.name,
                modifier = modifier

                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonStat4.point.toString(),
                modifier = modifier

                    .wrapContentWidth(Alignment.End)
            )
        }

         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = pokemonItem.pokemonStat5.name,
                modifier = modifier

                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonStat5.point.toString(),
                modifier = modifier

                    .wrapContentWidth(Alignment.End)
            )
        }
    }
}