package com.example.juaraandroid_pokemonapp.feature.detail.tabslayout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.juaraandroid_pokemonapp.feature.detail.*
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonAreaState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonCharacteristicState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonSpeciesState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonStatState

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    companion object {
        private const val TAB_BIO = "Bio"
        private const val TAB_STAT = "Stat"
        private const val TAB_SPECIES = "Species"
    }

    data class PokemonBio(
        val data: DetailPokemonStatState,
        val characteristic: DetailPokemonCharacteristicState,
        val area: DetailPokemonAreaState
    ) :
        TabItem(TAB_BIO, {
            Column {
                DetailPokemonBioSection(modifier = Modifier.fillMaxWidth(), data = data)
                DetailPokemonCharacteristicSection(
                    modifier = Modifier.fillMaxWidth(),
                    data = characteristic
                )
                DetailPokemonTypeSection(modifier = Modifier.fillMaxWidth(), data = data)
                DetailPokemonAreaSection(modifier = Modifier.fillMaxWidth(), area = area)
            }
        })

    data class PokemonStat(val data: DetailPokemonStatState) :
        TabItem(TAB_STAT, { DetailPokemonStatSection(data = data) })

    data class PokemonSpecies(val data: DetailPokemonSpeciesState) :
        TabItem(TAB_SPECIES, { DetailPokemonSpeciesSection(data = data) })


}
