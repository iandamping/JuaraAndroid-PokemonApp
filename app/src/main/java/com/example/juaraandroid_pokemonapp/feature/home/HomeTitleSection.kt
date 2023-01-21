package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.juaraandroid_pokemonapp.R


@Composable
fun HomeTitleSection(
    modifier: Modifier = Modifier,
    onIconSearchClick: () -> Unit,
    onIconQuizClick: () -> Unit
) {
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

        IconButton(
            onClick = {
                onIconQuizClick.invoke()
            },
            modifier = Modifier
                .constrainAs(iconQuizRef) {
                    top.linkTo(parent.top)
                    end.linkTo(iconSearchRef.start)
                    baseline.linkTo(titleRef.baseline)
                    height = Dimension.fillToConstraints
                }
        ) {
            Icon(
                imageVector = Icons.Default.Quiz,
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
                end.linkTo(iconSearchRef.start)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
            text = stringResource(R.string.sub_main_title),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
        )
    }
}