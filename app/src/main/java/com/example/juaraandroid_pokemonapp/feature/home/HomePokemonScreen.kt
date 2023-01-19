package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juaraandroid_pokemonapp.R

@Composable
fun HomePokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSelectedPokemon: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val state by viewModel.state.collectAsState()
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (lazyRowRef, errorHandlingRef) = createRefs()
        when {
            state.data.isNotEmpty() -> {
                LazyHorizontalGrid(
                    modifier = Modifier.constrainAs(lazyRowRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                    rows = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items = state.data, key = { key -> key.pokemonId }) { pokemonItem ->
                        ItemPokemonScreen(
                            data = pokemonItem,
                            onSelectedPokemon = { selectedPokemonId ->
                                onSelectedPokemon.invoke(selectedPokemonId)
                            })
                    }
                }
            }
            state.failedMessage.isNotEmpty() -> {
                Column(
                    modifier = Modifier.constrainAs(errorHandlingRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.error_image),
                        contentDescription = stringResource(id = R.string.no_data_available)
                    )

                    Text(
                        text = stringResource(id = R.string.no_data_available),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }

    }

}