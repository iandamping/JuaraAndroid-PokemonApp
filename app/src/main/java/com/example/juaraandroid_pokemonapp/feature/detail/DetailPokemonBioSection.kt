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

@Composable
fun DetailPokemonBioSection(modifier: Modifier = Modifier, data: DetailPokemonStatState) {

    when {
        data.data != null -> {
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
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
                    text = data.data.pokemonName,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }

            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
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
                        data.data.pokemonWeight.toString()
                    ),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }

            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
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
                        data.data.pokemonHeight.toString()
                    ),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }
        }

        data.failedMessage.isNotEmpty() -> {
            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
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


}