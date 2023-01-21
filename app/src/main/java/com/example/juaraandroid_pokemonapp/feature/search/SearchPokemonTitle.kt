package com.example.juaraandroid_pokemonapp.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.juaraandroid_pokemonapp.R

@Composable
fun SearchPokemonTitle(
    modifier: Modifier = Modifier,
    userSearch: String,
    isGridOrList: Boolean,
    isGridOrListClicked: (Boolean) -> Unit,
    onUserSearch: (String) -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (titleRef, secondTitleRef, iconFilterRef, searchRef) = createRefs()
        Image(
            modifier = Modifier
                .clickable {
                    isGridOrListClicked.invoke(!isGridOrList)
                }
                .constrainAs(iconFilterRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(secondTitleRef.bottom)
                    height = Dimension.fillToConstraints
                }, imageVector = if (isGridOrList) {
                Icons.Default.GridOn
            } else Icons.Default.Sort,
            contentDescription = null
        )

        Text(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(iconFilterRef.start)
                width = Dimension.fillToConstraints
            },
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
        )

        Text(
            modifier = Modifier.constrainAs(secondTitleRef) {
                top.linkTo(titleRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(iconFilterRef.start)
                width = Dimension.fillToConstraints
            },
            text = stringResource(R.string.sub_search_title),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
        )

        OutlinedTextField(
            modifier = Modifier.constrainAs(searchRef) {
                top.linkTo(secondTitleRef.bottom, 8.dp)
                start.linkTo(secondTitleRef.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            value = userSearch,
            onValueChange = { query ->
                onUserSearch.invoke(query)
            }, placeholder = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.search_pokemon),
                        style = MaterialTheme.typography.body2
                    )
                }
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        onUserSearch.invoke("")
                    },
                    imageVector = Icons.Default.HighlightOff,
                    contentDescription = stringResource(R.string.clear_search)
                )
            },
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            singleLine = true
        )

    }
}