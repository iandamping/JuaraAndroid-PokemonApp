package com.example.juaraandroid_pokemonapp.feature.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.PokemonQuizViewModel

@Composable
fun QuizPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonQuizViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()


    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (lazyRowRef, errorHandlingRef) = createRefs()
        val scrollState = rememberLazyListState()
        when {
            state.data.isNotEmpty() -> {
                LazyRow(
                    modifier = Modifier.constrainAs(lazyRowRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items = state.data, key = { key -> key.pokemonId }) { pokemonItem ->
                        val randomName: MutableList<String> =
                            state.data.shuffled().take(2)
                                .filter { it.pokemonName != pokemonItem.pokemonName }
                                .map { it.pokemonName }.toMutableList()
                        randomName.add(pokemonItem.pokemonName)

                        ItemQuizPokemonScreen(
                            pokemonImage = pokemonItem.pokemonImage,
                            randomName = randomName,
                            pokemonName = pokemonItem.pokemonName
                        )
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