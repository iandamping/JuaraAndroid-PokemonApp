package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species

import com.example.juaraandroid_pokemonapp.DummyPokemon
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup.RemotePokemonEggGroupManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup.RemotePokemonEggGroupManagerImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RemotePokemonSpeciesManagerImplTest{


    private val api: ApiInterface = mockk()
    private val baseSource: BaseSource = mockk()
    private lateinit var sut: RemotePokemonSpeciesManager

    @Before
    fun setup() {
        sut = RemotePokemonSpeciesManagerImpl(baseSource, api)
    }


    @Test
    fun `getDetailSpeciesPokemon success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonSpecies(any())) } returns ApiResult.Success(
            DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL
        )
        //when
        val results = sut.getDetailSpeciesPokemon(1) as ApiResult.Success
        //then
        coVerify { api.getPokemonSpecies(any()) }
        assertEquals(DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL, results.data)
        assertEquals(
            DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL.pokemonCaptureRate,
            results.data.pokemonCaptureRate
        )
        assertEquals(
            DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL.pokemonHappiness,
            results.data.pokemonHappiness
        )
        assertEquals(
            DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL.pokemonColor?.pokemonColor,
            results.data.pokemonColor?.pokemonColor
        )
        assertEquals(
            DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL.pokemonShape?.pokemonShape,
            results.data.pokemonShape?.pokemonShape
        )
        assertEquals(
            DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL.pokemonGeneration?.pokemonGenerationLString,
            results.data.pokemonGeneration?.pokemonGenerationLString
        )

    }

    @Test
    fun `getDetailSpecies Pokemon failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonSpecies(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonSpecies(any())) } returns ApiResult.Error(
            IOException(NetworkConstant.NETWORK_ERROR)
        )
        //when
        val results = sut.getDetailSpeciesPokemon(1) as ApiResult.Error
        //then
        coVerify { api.getPokemonSpecies(any()) }

        assertEquals(NetworkConstant.NETWORK_ERROR, results.exception.message)
    }

}