package com.example.juaraandroid_pokemonapp.feature.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.quiz.quizDialog.QuizIntroDialog

@Composable
fun QuizPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonQuizViewModel = hiltViewModel(),
    onDetailScreenIsClicked: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    var introDialogState by rememberSaveable {
        mutableStateOf(true)
    }

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
                    flingBehavior = rememberSnapFlingBehavior(scrollState),
                    state = scrollState
                ) {
                    items(
                        items = state.data.take(10),
                        key = { key -> key.pokemonId }) { pokemonItem ->
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

        QuizIntroDialog(
            modifier = Modifier.size(150.dp),
            dialogState = introDialogState,
            onDismissDialogRequest = { state ->
                introDialogState = state
            }
        )


    }
}