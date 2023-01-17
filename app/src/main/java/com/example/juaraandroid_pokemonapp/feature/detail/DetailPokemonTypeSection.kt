package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_SKILL_MONS
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_TYPE_MONS

@Composable
fun DetailPokemonTypeSection(modifier: Modifier = Modifier, pokemonItem: PokemonDetail) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Type :",
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonType0, modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }
        if (pokemonItem.pokemonType1 != ONE_TYPE_MONS) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "",
                    modifier = modifier
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = pokemonItem.pokemonType1,
                    modifier = modifier
                        .wrapContentWidth(Alignment.End)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Ability :",
                modifier = modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = pokemonItem.pokemonAbility1,
                modifier = modifier
                    .wrapContentWidth(Alignment.End)
            )
        }
        if (pokemonItem.pokemonAbility2 != ONE_SKILL_MONS) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "",
                    modifier = modifier
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = pokemonItem.pokemonAbility2,
                    modifier = modifier
                        .wrapContentWidth(Alignment.End)
                )
            }
        }
    }



}