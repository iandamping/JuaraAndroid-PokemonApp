package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonStatState
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_SKILL_MONS
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_TYPE_MONS

@Composable
fun DetailPokemonTypeSection(modifier: Modifier = Modifier, data: DetailPokemonStatState) {
    when {
        data.data != null -> {
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.type_name),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = LatoFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = data.data.pokemonType0, modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }

            if (data.data.pokemonType1 != ONE_TYPE_MONS) {
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonType1,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }
            }

            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.ability_name),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = LatoFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = data.data.pokemonAbility1,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }

            if (data.data.pokemonAbility2 != ONE_SKILL_MONS) {
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonAbility2,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }
            }
        }
        data.failedMessage.isNotEmpty() -> {
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.pokemon_type_name),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = LatoFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = stringResource(id = R.string.no_data_available),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }
        }
    }


}