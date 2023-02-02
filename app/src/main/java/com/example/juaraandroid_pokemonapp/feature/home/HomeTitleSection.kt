package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.util.PokemonConstant


@Composable
fun HomeTitleSection(
    modifier: Modifier = Modifier,
    isQuizEligibleToShown: Boolean,
    onIconSearchClick: () -> Unit,
    onIconQuizClick: () -> Unit
) {
    val density = LocalDensity.current

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            PokemonConstant.LOTTIE_GAME_ANIMATION_LOADING
        )
    )

    val progress by animateLottieCompositionAsState(
        composition
    )



    ConstraintLayout(modifier = modifier) {
        val (titleRef, secondTitleRef, iconSearchRef, iconQuizRef) = createRefs()
        IconButton(
            onClick = {
                onIconSearchClick.invoke()
            },
            modifier = Modifier
                .constrainAs(iconSearchRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    baseline.linkTo(titleRef.baseline)
                    height = Dimension.fillToConstraints
                }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                tint = Color.Black
            )
        }

        Text(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(iconSearchRef.start)
                width = Dimension.fillToConstraints
            },
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
        )

        Text(
            modifier = Modifier.constrainAs(secondTitleRef) {
                top.linkTo(titleRef.bottom)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
            text = stringResource(R.string.sub_main_title),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
        )

        AnimatedVisibility(
            modifier = Modifier.constrainAs(iconQuizRef) {
                top.linkTo(titleRef.bottom)
                end.linkTo(parent.end)
            },
            visible = isQuizEligibleToShown,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()

        ) {

            LottieAnimation(
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onIconQuizClick.invoke()
                    },
                composition = composition,
                progress = { progress },
            )


        }
    }
}