package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juaraandroid_pokemonapp.feature.HomeViewModel

@Composable
fun HomePokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSelectedPokemon: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val state by viewModel.state.collectAsState()
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val lazyRowRef = createRef()
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
//                LazyRow(
//                    modifier = Modifier.constrainAs(lazyRowRef) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                        bottom.linkTo(parent.bottom)
//                    },
//                    verticalAlignment = Alignment.CenterVertically,
//                    contentPadding = PaddingValues(8.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    state = scrollState,
////                    flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)
//                ) {
//                    items(items = state.data, key = { key -> key.pokemonId }) { pokemonItem ->
//                        ItemPokemonScreen(data = pokemonItem, onSelectedPokemon = {
//
//                        })
//                    }
//                }
            }
            state.failedMessage.isNotEmpty() -> {

            }
        }

    }

}