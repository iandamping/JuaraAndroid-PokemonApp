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
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonStatState
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily

@Composable
fun DetailPokemonStatSection(modifier: Modifier = Modifier, data: DetailPokemonStatState) {
    Column(modifier = modifier) {
        when {
            data.data != null -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${data.data.pokemonStat0.name} :",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonStat0.point.toString(),
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${data.data.pokemonStat1.name} :",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonStat1.point.toString(),
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${data.data.pokemonStat2.name} :",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonStat2.point.toString(),
                        modifier = modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${data.data.pokemonStat3.name} :",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier

                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonStat3.point.toString(),
                        modifier = modifier

                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${data.data.pokemonStat4.name} :",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier

                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonStat4.point.toString(),
                        modifier = modifier

                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${data.data.pokemonStat5.name} :",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = LatoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = modifier

                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = data.data.pokemonStat5.point.toString(),
                        modifier = modifier

                            .wrapContentWidth(Alignment.End)
                    )
                }
            }
            data.failedMessage.isNotEmpty() -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.stat_name),
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