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
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonCharacteristicState
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily

@Composable
fun DetailPokemonCharacteristicSection(
    modifier: Modifier = Modifier,
    data: DetailPokemonCharacteristicState
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
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
            data.characteristic.isNotEmpty() -> {
                Text(
                    text = data.characteristic,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }
            data.failedMessage.isNotEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_data_available),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }

        }


    }
}