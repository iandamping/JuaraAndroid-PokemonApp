package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.GET_POKEMON
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.GET_POKEMON_AREAS
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.GET_POKEMON_CHARACTERISTIC
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ApiInterface {

    @GET(GET_POKEMON)
    suspend fun getMainPokemon(): Response<PokemonMainResponse>

    @GET(GET_POKEMON)
    suspend fun getPaginationMainPokemon(@Query("offset") offset: Int): Response<PokemonMainResponse>

    @GET
    suspend fun getPokemon(@Url url: String): PokemonDetailResponse

    @GET("$GET_POKEMON/{name}")
    suspend fun getPokemonDirectByName(@Path("name") name: String): PokemonDetailResponse

    @GET
    suspend fun getPokemonEggGroup(@Url url: String): PokemonEggGroupResponse

    @GET
    suspend fun getPokemonEvolution(@Url url: String): PokemonEvolutionResponse

    @GET("$GET_POKEMON_CHARACTERISTIC/{id}")
    suspend fun getPokemonCharacteristic(@Path("id") id: Int): Response<PokemonCharacteristicResponse>

    @GET("$GET_POKEMON/{id}/$GET_POKEMON_AREAS")
    suspend fun getPokemonLocationAreas(@Path("id") id: Int): Response<List<PokemonAreasResponse>>

    @GET("$GET_POKEMON/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): Response<PokemonDetailResponse>

    @GET("$GET_POKEMON/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Response<PokemonDetailResponse>

    @GET("${NetworkConstant.GET_POKEMON_SPECIES}/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): Response<PokemonSpeciesDetailResponse>
}