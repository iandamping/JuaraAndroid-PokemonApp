package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area

import com.example.juaraandroid_pokemonapp.DummyPokemon
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RemotePokemonAreaManagerImplTest {

    private val api: ApiInterface = mockk()
    private val baseSource: BaseSource = mockk()
    private lateinit var sut: RemotePokemonAreaManager

    @Before
    fun setup() {
        sut = RemotePokemonAreaManagerImpl(baseSource, api)
    }


    @Test
    fun `getPokemonLocationAreas success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonLocationAreas(any())) } returns ApiResult.Success(
            DummyPokemon.DUMMY_LIST_POKEMON_AREA
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as ApiResult.Success
        //then
        coVerify { api.getPokemonLocationAreas(any()) }
        assertEquals(DummyPokemon.DUMMY_LIST_POKEMON_AREA[0].area.name, results.data[0])
        assertEquals(DummyPokemon.DUMMY_LIST_POKEMON_AREA[1].area.name, results.data[1])
        assertEquals(DummyPokemon.DUMMY_LIST_POKEMON_AREA[2].area.name, results.data[2])
    }

    @Test
    fun `getPokemonLocationAreas failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonLocationAreas(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonLocationAreas(any())) } returns ApiResult.Error(
            Exception(
                NetworkConstant.NETWORK_ERROR
            )
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as ApiResult.Error
        //then
        coVerify { api.getPokemonLocationAreas(any()) }
        assertEquals(NetworkConstant.NETWORK_ERROR, results.exception.message)
    }

    @Test
    fun `getPokemonLocationAreas failed when Api return empty list of data`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonLocationAreas(any())) } returns ApiResult.Success(
            listOf()
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as ApiResult.Error
        //then
        coVerify { api.getPokemonLocationAreas(any()) }
        assertEquals(NetworkConstant.EMPTY_DATA, results.exception.message)
    }
}