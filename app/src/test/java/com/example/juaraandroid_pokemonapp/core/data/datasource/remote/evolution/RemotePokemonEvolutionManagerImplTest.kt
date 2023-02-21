package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution

import com.example.juaraandroid_pokemonapp.DummyPokemon
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RemotePokemonEvolutionManagerImplTest {


    private val api: ApiInterface = mockk()
    private val baseSource: BaseSource = mockk()
    private lateinit var sut: RemotePokemonEvolutionManager

    @Before
    fun setup() {
        sut = RemotePokemonEvolutionManagerImpl(baseSource, api)
    }


    @Test
    fun `getPokemonEvolution success and return value`() = runTest {
        //given
        coEvery { api.getPokemonEvolution(any()) } returns DummyPokemon.DUMMY_POKEMON_EVOLUTION
        //when
        val results = sut.getPokemonEvolution("a")
        //then
        coVerify { api.getPokemonEvolution(any()) }
        assertEquals(
            DummyPokemon.DUMMY_POKEMON_EVOLUTION.evolutionChain.evolveTo.first().evolvingPokemonSpecies,
            results
        )
    }

    @Test
    fun `getPokemonEvolution failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemonEvolution(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        //when
        try {
            sut.getPokemonEvolution("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            assertEquals(e.message, NetworkConstant.NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemonEvolution(any()) }
        assertTrue(isExceptionThrown)
    }


}