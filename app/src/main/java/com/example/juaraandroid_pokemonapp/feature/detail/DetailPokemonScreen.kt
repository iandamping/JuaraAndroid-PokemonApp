package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R

@Composable
fun DetailPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.detailState.collectAsState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (imageRef, spriteImageRef, bioRef, statSectionRef, typeSectionRef, errorHandlingRef) = createRefs()
        val imageGuideLine = createGuidelineFromTop(0.3f)

        when {
            state.data != null -> {
                AsyncImage(
                    modifier = Modifier.constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(imageGuideLine)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.data?.pokemonImage)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.placeholder_image),
                    contentDescription = "pokemon image"
                )
                DetailPokemonSpriteImageSection(
                    modifier = Modifier
                        .constrainAs(spriteImageRef) {
                            top.linkTo(imageRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }, pokemonItem = state.data!!
                )

                DetailPokemonBioSection(
                    modifier = Modifier
                        .constrainAs(bioRef) {
                            top.linkTo(spriteImageRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }, pokemonItem = state.data!!
                )

                DetailPokemonStatSection(
                    modifier = Modifier
                        .constrainAs(statSectionRef) {
                            top.linkTo(bioRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }, pokemonItem = state.data!!
                )

                DetailPokemonTypeSection(
                    modifier = Modifier
                        .constrainAs(typeSectionRef) {
                            top.linkTo(statSectionRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }, pokemonItem = state.data!!
                )
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
                        painter = painterResource(id = R.drawable.error_image),
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