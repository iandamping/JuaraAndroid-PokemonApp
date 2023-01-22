package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonAreaState
import com.example.juaraandroid_pokemonapp.theme.LatoFontFamily

@Composable
fun DetailPokemonAreaSection(modifier: Modifier = Modifier, area: DetailPokemonAreaState) {
    Row(
        modifier = modifier,
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

        when {
            area.data.isNotEmpty() -> {
                LazyColumn(modifier = Modifier.wrapContentWidth(Alignment.End)) {
                    items(items = area.data) { pokemonArea ->
                        Text(text = pokemonArea, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
            area.failedMessage.isNotEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_data_available),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )
            }
        }
    }
}