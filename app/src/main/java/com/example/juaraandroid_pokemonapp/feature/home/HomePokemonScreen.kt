package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.juaraandroid_pokemonapp.feature.PokemonPaginationViewModel

@Composable
fun HomePokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonPaginationViewModel = hiltViewModel(),
    onSelectedPokemon: (Int) -> Unit,
    onIconSearchClick: () -> Unit,
    onIconQuizClick: () -> Unit
) {
    val lazyPagingItems = viewModel.pokemonPagination.collectAsLazyPagingItems()

    ConstraintLayout(modifier = modifier.padding(8.dp)) {
        val (titleRef, contentRef) = createRefs()
        val titleGuideLine = createGuidelineFromTop(0.15f)
        HomeTitleSection(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(titleGuideLine)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }, onIconSearchClick = onIconSearchClick,
            onIconQuizClick = onIconQuizClick
        )
        HomePaginationContentSection(
            modifier = Modifier.constrainAs(contentRef) {
                top.linkTo(titleGuideLine, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            lazyPagingItems = lazyPagingItems,
            onSelectedPokemon = { pokemonId ->
                onSelectedPokemon.invoke(pokemonId)
            },
        )
    }

}