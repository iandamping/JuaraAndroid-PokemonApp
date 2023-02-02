package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.feature.home.util.pagingGridItems
import com.example.juaraandroid_pokemonapp.feature.shimmer.ShimmerAnimation
import kotlinx.coroutines.launch

@Composable
fun HomePaginationContentSection(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<PokemonDetail>,
    onSelectedPokemon: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listGridState = rememberLazyGridState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (paginationRef, btnBackToTopRef) = createRefs()

        LazyVerticalGrid(
            modifier = Modifier.constrainAs(paginationRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = listGridState
        ) {

            pagingGridItems(items = lazyPagingItems,
                key = { key -> key.pokemonId }) { pokemonItem ->
                if (pokemonItem != null) {
                    ItemPokemonScreen(
                        data = pokemonItem,
                        isGridOrList = true,
                        onSelectedPokemon = { selectedPokemonId ->
                            onSelectedPokemon.invoke(selectedPokemonId)
                        })
                }
            }

            when (lazyPagingItems.loadState.refresh) { //FIRST LOAD
                is LoadState.Error -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.fillMaxWidth(),
                                painter = painterResource(id = R.drawable.ic_no_data),
                                contentDescription = stringResource(id = R.string.no_data_available)
                            )

                            Text(
                                text = stringResource(id = R.string.no_data_available),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                }
                is LoadState.Loading -> { // Loading UI
                    items(10) {
                        ShimmerAnimation {
                            HomeItemShimmer(brush = it, isGridOrList = true)
                        }
                    }
                }
                else -> {}
            }

            when (lazyPagingItems.loadState.append) { // Pagination
                is LoadState.Error -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.fillMaxWidth(),
                                painter = painterResource(id = R.drawable.ic_no_data),
                                contentDescription = stringResource(id = R.string.no_data_available)
                            )

                            Text(
                                text = stringResource(id = R.string.no_data_available),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                }
                is LoadState.Loading -> { // Pagination Loading UI
                    items(10) {
                        ShimmerAnimation {
                            HomeItemShimmer(brush = it, isGridOrList = true)
                        }
                    }
                }
                else -> {}
            }
        }

        val gridShowScrollToTopButton by remember {
            derivedStateOf {
                listGridState.firstVisibleItemIndex > 0
            }
        }

        AnimatedVisibility(
            visible = gridShowScrollToTopButton,
            modifier = Modifier.constrainAs(btnBackToTopRef) {
                bottom.linkTo(parent.bottom, 12.dp)
                centerHorizontallyTo(parent)
            }) {
            Button(shape = RoundedCornerShape(20.dp),
                onClick = {
                    coroutineScope.launch {
                        // Animate scroll to the first item
                        listGridState.animateScrollToItem(index = 0)
                    }
                }
            ) {
                Icon(
                    Icons.Default.ArrowUpward,
                    contentDescription = "back to top"
                )
                Text(text = "back to top")
            }
        }

    }
}