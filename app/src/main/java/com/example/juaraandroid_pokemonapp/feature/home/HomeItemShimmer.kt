package com.example.juaraandroid_pokemonapp.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun HomeItemShimmer(
    modifier: Modifier = Modifier,
    isGridOrList: Boolean,
    brush: Brush
) {
    Card(
        modifier = modifier
            .size(
                if (isGridOrList) 200.dp else 350.dp
            )
            .fillMaxSize(),
        elevation = 8.dp
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush),
        )
    }


}