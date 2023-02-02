package com.example.juaraandroid_pokemonapp.feature.detail.detailShimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun DetailContentShimmer(
    modifier: Modifier = Modifier,
    brush: Brush
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        //image
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .height(250.dp)
                .background(brush = brush),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        //sprite image
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
                .background(brush = brush),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        //tab text
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
                .background(brush = brush),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        //tab content
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .height(250.dp)
                .background(brush = brush),
        )
    }

}