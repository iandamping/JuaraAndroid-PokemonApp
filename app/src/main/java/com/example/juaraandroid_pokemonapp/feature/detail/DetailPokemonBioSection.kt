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
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonAreaState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonCharacteristicState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonStatState
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily
import com.example.juaraandroid_pokemonapp.util.PokemonConstant

@Composable
fun DetailPokemonBioSection(
    modifier: Modifier = Modifier,
    dataStat: DetailPokemonStatState,
    dataCharacteristic: DetailPokemonCharacteristicState,
    area: DetailPokemonAreaState
) {
    Column(modifier = modifier) {
        when {
            dataStat.data != null -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.bio_name),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = dataStat.data.pokemonName,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.title_weight),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.pokemon_weight,
                            dataStat.data.pokemonWeight.toString()
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.title_height),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.pokemon_height,
                            dataStat.data.pokemonHeight.toString()
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
                        text = dataStat.data.pokemonType0, modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                if (dataStat.data.pokemonType1 != PokemonConstant.ONE_TYPE_MONS) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "",
                            modifier = Modifier
                                .wrapContentWidth(Alignment.Start)
                        )
                        Text(
                            text = dataStat.data.pokemonType1,
                            modifier = Modifier
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
                        text = dataStat.data.pokemonAbility1,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                if (dataStat.data.pokemonAbility2 != PokemonConstant.ONE_SKILL_MONS) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "",
                            modifier = Modifier
                                .wrapContentWidth(Alignment.Start)
                        )
                        Text(
                            text = dataStat.data.pokemonAbility2,
                            modifier = Modifier
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                }

            }

            dataStat.failedMessage.isNotEmpty() -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.bio_name),
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

        when {
            dataCharacteristic.characteristic.isNotEmpty() -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.characteristic_name),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    when {
                        dataCharacteristic.characteristic.isNotEmpty() -> {
                            Text(
                                text = dataCharacteristic.characteristic,
                                modifier = Modifier
                                    .wrapContentWidth(Alignment.End)
                            )
                        }
                        dataCharacteristic.failedMessage.isNotEmpty() -> {
                            Text(
                                text = stringResource(id = R.string.no_data_available),
                                modifier = Modifier
                                    .wrapContentWidth(Alignment.End)
                            )
                        }

                    }


                }
            }

            dataCharacteristic.failedMessage.isNotEmpty() -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.characteristic_name),
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

        when {
            area.data.isNotEmpty() -> {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.encounters_name),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )

                    Text(
                        text = if (area.data.isNotEmpty()) area.data.first() else "",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }


                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )

                    Text(
                        text = if (area.data.isNotEmpty()) area.data[1] else "",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )

                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )

                    Text(
                        text = if (area.data.isNotEmpty()) area.data[2] else "",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )

                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )

                    Text(
                        text = if (area.data.isNotEmpty()) area.data.last() else "",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )

                }
            }

            area.failedMessage.isNotEmpty() -> {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.encounters_name),
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


}