package com.example.juaraandroid_pokemonapp.feature.detail.detailShimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun EvolveItemShimmer(
    modifier: Modifier = Modifier,
    brush: Brush
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp), elevation = 8.dp
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush),
        )
    }
}