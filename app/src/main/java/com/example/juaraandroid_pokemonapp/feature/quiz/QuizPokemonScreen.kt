package com.example.juaraandroid_pokemonapp.feature.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juaraandroid_pokemonapp.R
import kotlinx.coroutines.launch

@Composable
fun QuizPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonQuizViewModel = hiltViewModel(),
    onDetailScreenIsClicked: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (lazyRowRef, btnBackToStartRef, errorHandlingRef) = createRefs()
        val startGuideLine = createGuidelineFromTop(0.25f)
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
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
                    flingBehavior = rememberSnapFlingBehavior(scrollState),
                    state = scrollState
                ) {
                    items(items = state.data, key = { key -> key.pokemonId }) { pokemonItem ->
                        val randomName: MutableList<String> =
                            state.data.shuffled().take(2)
                                .filter { it.pokemonName != pokemonItem.pokemonName }
                                .map { it.pokemonName }.toMutableList()
                        randomName.add(pokemonItem.pokemonName)

                        ItemQuizPokemonScreen(
                            pokemonId = pokemonItem.pokemonId,
                            pokemonImage = pokemonItem.pokemonImage,
                            randomName = randomName,
                            pokemonName = pokemonItem.pokemonName,
                            onDetailScreenIsClicked = { selectedId ->
                                onDetailScreenIsClicked.invoke(selectedId)
                            }
                        )
                    }
                }

                val listShowScrollToStartButton by remember {
                    derivedStateOf {
                        scrollState.firstVisibleItemIndex > 0
                    }
                }

                AnimatedVisibility(
                    visible = listShowScrollToStartButton,
                    modifier = Modifier.constrainAs(btnBackToStartRef) {
                        top.linkTo(startGuideLine)
                        start.linkTo(parent.start, 12.dp)
                    }) {
                    Button(shape = RoundedCornerShape(20.dp),
                        onClick = {
                            coroutineScope.launch {
                                scrollState.animateScrollToItem(index = 0)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.start_position)
                        )
                        Text(text = stringResource(R.string.start_position))
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