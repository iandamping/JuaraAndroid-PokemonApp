package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonSpeciesState
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily
import com.example.juaraandroid_pokemonapp.util.PokemonConstant

@Composable
fun DetailPokemonSpeciesSection(modifier: Modifier = Modifier, data: DetailPokemonSpeciesState) {
    Column(modifier = modifier) {
        when {
            data.data != null -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.generation_name),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.generation,
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dominant_color),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.color,
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.pokemon_shape),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.shape,
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.growth_rate),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.growthRate,
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.happiness_name),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.happiness.toString(),
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.capture_rate),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = "${data.data.captureRate} %",
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.habitat_name),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.habitat,
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.egg_group),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.eggGroup1,
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                if (data.data.eggGroup2 != PokemonConstant.ONE_EGG_MONS) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "",
                            modifier = modifier
                                .wrapContentWidth(Alignment.Start)
                        )
                        Text(
                            text = data.data.eggGroup2,
                            modifier = modifier
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                }
            }

            data.failedMessage.isNotEmpty() -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.pokemon_species_name),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = stringResource(id = R.string.no_data_available),
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }
            }
        }
    }

}