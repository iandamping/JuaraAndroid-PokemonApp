package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon

import com.example.juaraandroid_pokemonapp.DummyPokemon
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonMainResponse
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RemotePokemonManagerImplTest {


    private val api: ApiInterface = mockk()
    private val baseSource: BaseSource = mockk()
    private lateinit var sut: RemotePokemonManager

    @Before
    fun setup() {
        sut = RemotePokemonManagerImpl(baseSource, api)
    }


    @Test
    fun `getPokemon success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getMainPokemon()) } returns ApiResult.Success(
            DummyPokemon.DUMMY_POKEMON_MAIN_RESPONSE
        )
        //when
        val results = sut.getPokemon() as ApiResult.Success
        //then
        coVerify { api.getMainPokemon() }
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults, results.data)
        Assert.assertEquals(DummyPokemon.DUMMY_URL_POKEMON_RESULTS_1, results.data[0])
        Assert.assertEquals(DummyPokemon.DUMMY_URL_POKEMON_RESULTS_2, results.data[1])
        Assert.assertEquals(DummyPokemon.DUMMY_URL_POKEMON_RESULTS_3, results.data[2])
    }

    @Test
    fun `getPokemon failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getMainPokemon() } throws IOException(NetworkConstant.NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getMainPokemon()) } returns ApiResult.Error(
            Exception(
                NetworkConstant.NETWORK_ERROR
            )
        )
        //when
        val results = sut.getPokemon() as ApiResult.Error
        //then
        coVerify { api.getMainPokemon() }
        //then
        Assert.assertEquals(NetworkConstant.NETWORK_ERROR, results.exception.message)
    }


    @Test
    fun `getPokemon failed when Api return empty list of data`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getMainPokemon()) } returns ApiResult.Success(
            PokemonMainResponse(pokemonResults = listOf())
        )
        //when
        val results = sut.getPokemon() as ApiResult.Error
        //then
        coVerify { api.getMainPokemon() }
        //then
        Assert.assertEquals(NetworkConstant.EMPTY_DATA, results.exception.message)
    }

    @Test
    fun `getPaginationPokemon success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPaginationMainPokemon(any())) } returns ApiResult.Success(
            DummyPokemon.DUMMY_POKEMON_MAIN_RESPONSE
        )
        //when
        val results = sut.getPaginationPokemon(2) as ApiResult.Success
        //then
        coVerify { api.getPaginationMainPokemon(any()) }
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults, results.data)
        Assert.assertEquals(DummyPokemon.DUMMY_URL_POKEMON_RESULTS_1, results.data[0])
        Assert.assertEquals(DummyPokemon.DUMMY_URL_POKEMON_RESULTS_2, results.data[1])
        Assert.assertEquals(DummyPokemon.DUMMY_URL_POKEMON_RESULTS_3, results.data[2])
    }

    @Test
    fun `getPaginationPokemon failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPaginationMainPokemon(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPaginationMainPokemon(any())) } returns ApiResult.Error(
            Exception(NetworkConstant.NETWORK_ERROR)
        )
        //when
        val results = sut.getPaginationPokemon(1) as ApiResult.Error
        //then
        coVerify { api.getPaginationMainPokemon(1) }
        //then
        Assert.assertEquals(NetworkConstant.NETWORK_ERROR, results.exception.message)
    }

    @Test
    fun `getPaginationPokemon failed when Api return empty list of data`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPaginationMainPokemon(any())) } returns ApiResult.Success(
            PokemonMainResponse(pokemonResults = listOf())
        )
        //when
        val results = sut.getPaginationPokemon(2) as ApiResult.Error
        //then
        coVerify { api.getPaginationMainPokemon(any()) }
        //then
        Assert.assertEquals(NetworkConstant.EMPTY_DATA, results.exception.message)
    }

    @Test
    fun `getDetailPokemon success and return value`() = runTest {
        //given
        coEvery { api.getPokemon(any()) } returns DummyPokemon.DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getDetailPokemon("a")
        //then
        coVerify { api.getPokemon(any()) }
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL, results)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonId, results.pokemonId)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonName, results.pokemonName)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonHeight, results.pokemonHeight)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonWeight, results.pokemonWeight)
    }

    @Test
    fun `getDetailPokemon failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemon(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        //when
        try {
            sut.getDetailPokemon("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NetworkConstant.NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemon(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

    @Test
    fun `getDetailPokemonDirectByName success and return value`() = runTest {
        //given
        coEvery { api.getPokemonDirectByName(any()) } returns DummyPokemon.DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getDetailPokemonDirectByName("a")
        //then
        coVerify { api.getPokemonDirectByName(any()) }
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL, results)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonId, results.pokemonId)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonName, results.pokemonName)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonHeight, results.pokemonHeight)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonWeight, results.pokemonWeight)
    }

    @Test
    fun `getDetailPokemonDirectByName failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemonDirectByName(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        //when
        try {
            sut.getDetailPokemonDirectByName("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NetworkConstant.NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemonDirectByName(any()) }
        Assert.assertTrue(isExceptionThrown)
    }





    @Test
    fun `getPokemonById success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonById(any())) } returns ApiResult.Success(
            DummyPokemon.DUMMY_POKEMON_DETAIL
        )
        //when
        val results = sut.getPokemonById(1) as ApiResult.Success
        //then
        coVerify { api.getPokemonById(any()) }
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL, results.data)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonId, results.data.pokemonId)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonName, results.data.pokemonName)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonHeight, results.data.pokemonHeight)
        Assert.assertEquals(DummyPokemon.DUMMY_POKEMON_DETAIL.pokemonWeight, results.data.pokemonWeight)
    }

    @Test
    fun `getPokemonById failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonById(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonById(any())) } returns ApiResult.Error(
            Exception(NetworkConstant.NETWORK_ERROR)
        )
        //when
        val results = sut.getPokemonById(1) as ApiResult.Error
        //then
        coVerify { api.getPokemonById(any()) }
        Assert.assertEquals(NetworkConstant.NETWORK_ERROR, results.exception.message)
    }


}