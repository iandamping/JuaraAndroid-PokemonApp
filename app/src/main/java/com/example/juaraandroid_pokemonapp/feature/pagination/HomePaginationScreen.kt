package com.example.juaraandroid_pokemonapp.feature.pagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.juaraandroid_pokemonapp.feature.PokemonPaginationViewModel
import com.example.juaraandroid_pokemonapp.feature.home.ItemPokemonScreen
import com.example.juaraandroid_pokemonapp.feature.pagination.util.pagingGridItems

@Composable
fun HomePaginationScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonPaginationViewModel = hiltViewModel(),
    onSelectedPokemon: (Int) -> Unit
) {

    val lazyPagingItems = viewModel.pokemonPagination.collectAsLazyPagingItems()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val paginationRef = createRef()

        LazyHorizontalGrid(
            modifier = Modifier.constrainAs(paginationRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            pagingGridItems(items = lazyPagingItems,
                key = { key -> key.pokemonId }) { pokemonItem ->
                if (pokemonItem != null) {
                    ItemPokemonScreen(
                        data = pokemonItem,
                        onSelectedPokemon = { selectedPokemonId ->
                            onSelectedPokemon.invoke(selectedPokemonId)
                        })
                }
            }
        }
    }

}