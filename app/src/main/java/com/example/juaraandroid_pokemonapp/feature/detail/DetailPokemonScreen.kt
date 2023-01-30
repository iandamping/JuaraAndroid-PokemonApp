package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.feature.detail.tabslayout.TabItem
import com.example.juaraandroid_pokemonapp.feature.detail.tabslayout.TabScreen
import com.example.juaraandroid_pokemonapp.feature.detail.tabslayout.TabsContent
import com.google.accompanist.pager.rememberPagerState

@Composable
fun DetailPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()

    val statState by viewModel.detailStatState.collectAsState()
    val characteristicState by viewModel.detailCharacteristicState.collectAsState()
    val areaState by viewModel.detailAreaState.collectAsState()
    val speciesState by viewModel.detailSpeciesState.collectAsState()

    Scaffold(modifier = modifier) { paddingValues ->
        ConstraintLayout(modifier = modifier.padding(paddingValues)) {


            val (imageRef, spriteImageRef, errorHandlingRef, tabsRef, tabContentRef) = createRefs()
            val imageGuideLine = createGuidelineFromTop(0.3f)

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
                            .data(statState.data?.pokemonImage)
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
                            }, pokemonItem = statState.data!!
                    )

                    ///tabs position
                    TabScreen(modifier = Modifier.constrainAs(tabsRef) {
                        top.linkTo(spriteImageRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }, tabs = tabs, pagerState = pagerState)

                    TabsContent(modifier = Modifier.constrainAs(tabContentRef) {
                        top.linkTo(tabsRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }.padding(8.dp), tabs = tabs, pagerState = pagerState)

                }
                statState.failedMessage.isNotEmpty() -> {
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


}