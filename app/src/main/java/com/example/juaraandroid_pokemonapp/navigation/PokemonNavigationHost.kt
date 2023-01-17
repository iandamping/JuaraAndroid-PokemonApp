package com.example.juaraandroid_pokemonapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.juaraandroid_pokemonapp.feature.detail.DetailPokemonScreen
import com.example.juaraandroid_pokemonapp.feature.home.HomePokemonScreen

@Composable
fun PokemonNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = PokemonNavigationScreen.HomeScreenNavigation.name,
        modifier = modifier
    ) {
        composable(PokemonNavigationScreen.HomeScreenNavigation.name) {
            HomePokemonScreen(modifier = Modifier.padding(8.dp),
                onSelectedPokemon = { id ->
                    navController.navigate("${PokemonNavigationScreen.DetailPokemonScreenNavigation.name}/$id")
                }
            )
        }
        composable(
            "${PokemonNavigationScreen.DetailPokemonScreenNavigation.name}/{${PokemonNavigationArgument.DetailPokemonId.name}}",
            arguments = listOf(navArgument(PokemonNavigationArgument.DetailPokemonId.name) {
                type = NavType.IntType
            })
        ) {
            DetailPokemonScreen(modifier = Modifier.padding(8.dp))
        }
    }
}