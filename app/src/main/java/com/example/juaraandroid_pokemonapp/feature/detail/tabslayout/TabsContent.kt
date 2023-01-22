package com.example.juaraandroid_pokemonapp.feature.detail.tabslayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@Composable
fun TabsContent(modifier: Modifier = Modifier, tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(modifier = modifier, state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}