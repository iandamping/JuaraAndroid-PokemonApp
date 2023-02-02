package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.detail.detailShimmer.DetailContentShimmer
import com.example.juaraandroid_pokemonapp.feature.detail.detailShimmer.EvolveItemShimmer
import com.example.juaraandroid_pokemonapp.feature.detail.detailShimmer.SimilarItemShimmer
import com.example.juaraandroid_pokemonapp.feature.detail.tabslayout.TabItem
import com.example.juaraandroid_pokemonapp.feature.detail.tabslayout.TabScreen
import com.example.juaraandroid_pokemonapp.feature.detail.tabslayout.TabsContent
import com.example.juaraandroid_pokemonapp.feature.shimmer.ShimmerAnimation
import com.google.accompanist.pager.rememberPagerState

@Composable
fun DetailPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    onSelectedPokemon: (Int) -> Unit
) {
    val pagerState = rememberPagerState()

    val statState by viewModel.detailStatState.collectAsState()
    val characteristicState by viewModel.detailCharacteristicState.collectAsState()
    val areaState by viewModel.detailAreaState.collectAsState()
    val speciesState by viewModel.detailSpeciesState.collectAsState()
    val evolution by viewModel.detailEvolutionState.collectAsState()
    val similarPokemon by viewModel.detailEggGroupState.collectAsState()

    Scaffold(modifier = modifier) { paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues),
        ) {
            if (statState.isLoading) {
                items(1) {
                    ShimmerAnimation {
                        DetailContentShimmer(brush = it)
                    }
                }
            }

            when {
                statState.data != null -> {
                    val tabs = listOf<TabItem>(
                        TabItem.PokemonBio(
                            data = statState,
                            characteristic = characteristicState,
                            area = areaState
                        ),
                        TabItem.PokemonStat(data = statState),
                        TabItem.PokemonSpecies(data = speciesState)
                    )
                    item {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(statState.data?.pokemonImage)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.placeholder_image),
                            contentDescription = stringResource(R.string.pokemon_image)
                        )

                        DetailPokemonSpriteImageSection(
                            modifier = Modifier.fillMaxWidth(), pokemonItem = statState.data!!
                        )

                        ///tabs position
                        TabScreen(
                            modifier = Modifier.fillMaxWidth(),
                            tabs = tabs,
                            pagerState = pagerState
                        )
                        //tab content
                        TabsContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            tabs = tabs,
                            pagerState = pagerState
                        )
                    }

                }
                statState.failedMessage.isNotEmpty() -> {

                    item {
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

            if (evolution.isLoading) {
                items(1) {
                    ShimmerAnimation {
                        EvolveItemShimmer(brush = it)
                    }
                }
            }


            item {
                if (evolution.data != null && statState.data != null) {
                    AnimatedVisibility(
                        visible = evolution.data!!.pokemonName != statState.data!!.pokemonName,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DetailPokemonEvolveScreen(
                            modifier = Modifier.fillMaxWidth(),
                            data = evolution,
                            onSelectedPokemon = { selectedId ->
                                onSelectedPokemon.invoke(selectedId)
                            })
                    }
                }
            }



            item {
                AnimatedVisibility(
                    visible = similarPokemon.data.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Pokemon from same Egg Group",
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            if (similarPokemon.isLoading) {
                items(4) {
                    ShimmerAnimation {
                        SimilarItemShimmer(brush = it)
                    }
                }
            }



            if (similarPokemon.data.isNotEmpty()) {
                items(
                    items = similarPokemon.data,
                    key = { key -> key.pokemonId }) { pokemon ->
                    DetailSimilarPokemonItemSection(modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(),
                        data = pokemon,
                        onSelectedPokemon = {
                            onSelectedPokemon.invoke(pokemon.pokemonId)
                        })
                }
            }
        }
    }


}