package com.example.juaraandroid_pokemonapp.feature.search

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchPokemonViewModel = hiltViewModel(),
    onSelectedPokemon: (Int) -> Unit
) {
    var isGridOrList by rememberSaveable {
        mutableStateOf(true)
    }
    val state by viewModel.uiState.collectAsState()
    val userSearch by viewModel.searchPokemon.collectAsState()

    ConstraintLayout(modifier = modifier.padding(8.dp)) {
        val (titleRef, contentRef) = createRefs()
        val titleGuideLine = createGuidelineFromTop(0.25f)
        SearchPokemonTitle(modifier = Modifier.constrainAs(titleRef) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(titleGuideLine)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }, isGridOrList = isGridOrList,
            userSearch = userSearch,
            onUserSearch = { query ->
                viewModel.setSearchPokemon(query)
            },
            isGridOrListClicked = { value ->
                isGridOrList = value
            }
        )
        SearchPokemonContentSection(
            modifier = Modifier.constrainAs(contentRef) {
                top.linkTo(titleRef.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            data = state,
            isGridOrList = isGridOrList,
            onSelectedPokemon = { pokemonId ->
                onSelectedPokemon.invoke(pokemonId)
            },
        )
    }
}