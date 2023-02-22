package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup

import com.example.juaraandroid_pokemonapp.DummyPokemon
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RemotePokemonEggGroupManagerImplTest {

    private val api: ApiInterface = mockk()
    private val baseSource: BaseSource = mockk()
    private lateinit var sut: RemotePokemonEggGroupManager

    @Before
    fun setup() {
        sut = RemotePokemonEggGroupManagerImpl(baseSource, api)
    }


    @Test
    fun `getPokemonEggGroup success and return value`() = runTest {
        //given
        coEvery { api.getPokemonEggGroup(any()) } returns DummyPokemon.DUMMY_POKEMON_EGG
        //when
        val results = sut.getPokemonEggGroup("a")
        //then
        coVerify { api.getPokemonEggGroup(any()) }
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_EGG.eggGroupSpecies, results)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_EGG.eggGroupName, "a")
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_EGG.eggGroupSpecies[0].name, "a")
    }

    @Test
    fun `getPokemonEggGroup failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemonEggGroup(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        //when
        try {
            sut.getPokemonEggGroup("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NetworkConstant.NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemonEggGroup(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

}