package com.example.juaraandroid_pokemonapp.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.home.ItemPokemonScreen
import com.example.juaraandroid_pokemonapp.feature.state.SearchPokemonState
import kotlinx.coroutines.launch

@Composable
fun SearchPokemonContentSection(
    modifier: Modifier,
    data: SearchPokemonState,
    isGridOrList: Boolean,
    onSelectedPokemon: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listGridState = rememberLazyGridState()
    val listState = rememberLazyListState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (contentRef, errorHandlingRef, btnBackToTopRef) = createRefs()

        when {
            data.data.isNotEmpty() -> {
                if (isGridOrList) {
                    LazyVerticalGrid(
                        modifier = Modifier.constrainAs(contentRef) {
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
                        items(items = data.data, key = { key -> key.pokemonId }) { pokemonItem ->
                            ItemPokemonScreen(
                                data = pokemonItem,
                                isGridOrList = isGridOrList,
                                onSelectedPokemon = { selectedPokemonId ->
                                    onSelectedPokemon.invoke(selectedPokemonId)
                                })
                        }
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier.constrainAs(contentRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        state = listState
                    ) {
                        items(items = data.data, key = { key -> key.pokemonId }) { pokemonItem ->
                            ItemPokemonScreen(
                                data = pokemonItem,
                                isGridOrList = isGridOrList,
                                onSelectedPokemon = { selectedPokemonId ->
                                    onSelectedPokemon.invoke(selectedPokemonId)
                                })
                        }

                    }
                }

                val listShowScrollToTopButton by remember {
                    derivedStateOf {
                        listState.firstVisibleItemIndex > 0
                    }
                }

                val gridShowScrollToTopButton by remember {
                    derivedStateOf {
                        listGridState.firstVisibleItemIndex > 0
                    }
                }

                AnimatedVisibility(
                    visible = if (isGridOrList) gridShowScrollToTopButton else listShowScrollToTopButton,
                    modifier = Modifier.constrainAs(btnBackToTopRef) {
                        bottom.linkTo(parent.bottom, 12.dp)
                        centerHorizontallyTo(parent)
                    }) {
                    Button(shape = RoundedCornerShape(20.dp),
                        onClick = {
                            coroutineScope.launch {
                                // Animate scroll to the first item
                                if (isGridOrList) {
                                    listGridState.animateScrollToItem(index = 0)
                                } else listState.animateScrollToItem(index = 0)
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
            data.failedMessage.isNotEmpty() -> {
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
    }

}