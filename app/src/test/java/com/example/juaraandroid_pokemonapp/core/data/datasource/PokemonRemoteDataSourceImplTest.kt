package com.example.juaraandroid_pokemonapp.core.data.datasource

import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_LIST_POKEMON_AREA
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_CHARACTERISTIC
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_DETAIL
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_EGG
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_EVOLUTION
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_MAIN_RESPONSE
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_URL_POKEMON_RESULTS_1
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_URL_POKEMON_RESULTS_2
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_URL_POKEMON_RESULTS_3
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.NETWORK_ERROR
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

class PokemonRemoteDataSourceImplTest {

    private val api: ApiInterface = mockk()
    private val baseSource: BaseSource = mockk()
    private lateinit var sut: PokemonRemoteDataSource

    @Before
    fun setup() {
        sut = PokemonRemoteDataSourceImpl(api = api, baseSource = baseSource)
    }

    @Test
    fun `getPokemon success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getMainPokemon()) } returns ApiResult.Success(
            DUMMY_POKEMON_MAIN_RESPONSE
        )
        //when
        val results = sut.getPokemon() as DataSourceResult.SourceValue
        //then
        coVerify { api.getMainPokemon() }
        Assert.assertEquals(DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults, results.data)
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_1, results.data[0])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_2, results.data[1])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_3, results.data[2])
    }

    @Test
    fun `getPokemon failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getMainPokemon() } throws IOException(NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getMainPokemon()) } returns ApiResult.Error(
            Exception(
                NETWORK_ERROR
            )
        )
        //when
        val results = sut.getPokemon() as DataSourceResult.SourceError
        //then
        coVerify { api.getMainPokemon() }
        //then
        Assert.assertEquals(NETWORK_ERROR, results.exception.message)
    }


    @Test
    fun `getPokemon failed when Api return empty list of data`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getMainPokemon()) } returns ApiResult.Success(
            PokemonMainResponse(pokemonResults = listOf())
        )
        //when
        val results = sut.getPokemon() as DataSourceResult.SourceError
        //then
        coVerify { api.getMainPokemon() }
        //then
        Assert.assertEquals(EMPTY_DATA, results.exception.message)
    }

    @Test
    fun `getPaginationPokemon success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPaginationMainPokemon(any())) } returns ApiResult.Success(
            DUMMY_POKEMON_MAIN_RESPONSE
        )
        //when
        val results = sut.getPaginationPokemon(2) as DataSourceResult.SourceValue
        //then
        coVerify { api.getPaginationMainPokemon(any()) }
        Assert.assertEquals(DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults, results.data)
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_1, results.data[0])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_2, results.data[1])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_3, results.data[2])
    }

    @Test
    fun `getPaginationPokemon failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPaginationMainPokemon(any()) } throws IOException(NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPaginationMainPokemon(any())) } returns ApiResult.Error(
            Exception(NETWORK_ERROR)
        )
        //when
        val results = sut.getPaginationPokemon(1) as DataSourceResult.SourceError
        //then
        coVerify { api.getPaginationMainPokemon(1) }
        //then
        Assert.assertEquals(NETWORK_ERROR, results.exception.message)
    }

    @Test
    fun `getPaginationPokemon failed when Api return empty list of data`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPaginationMainPokemon(any())) } returns ApiResult.Success(
            PokemonMainResponse(pokemonResults = listOf())
        )
        //when
        val results = sut.getPaginationPokemon(2) as DataSourceResult.SourceError
        //then
        coVerify { api.getPaginationMainPokemon(any()) }
        //then
        Assert.assertEquals(EMPTY_DATA, results.exception.message)
    }

    @Test
    fun `getDetailPokemon success and return value`() = runTest {
        //given
        coEvery { api.getPokemon(any()) } returns DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getDetailPokemon("a")
        //then
        coVerify { api.getPokemon(any()) }
        Assert.assertEquals(DUMMY_POKEMON_DETAIL, results)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonId, results.pokemonId)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonName, results.pokemonName)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonHeight, results.pokemonHeight)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonWeight, results.pokemonWeight)
    }

    @Test
    fun `getDetailPokemon failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemon(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getDetailPokemon("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemon(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

    @Test
    fun `getDetailPokemonDirectByName success and return value`() = runTest {
        //given
        coEvery { api.getPokemonDirectByName(any()) } returns DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getDetailPokemonDirectByName("a")
        //then
        coVerify { api.getPokemonDirectByName(any()) }
        Assert.assertEquals(DUMMY_POKEMON_DETAIL, results)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonId, results.pokemonId)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonName, results.pokemonName)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonHeight, results.pokemonHeight)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonWeight, results.pokemonWeight)
    }

    @Test
    fun `getDetailPokemonDirectByName failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemonDirectByName(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getDetailPokemonDirectByName("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemonDirectByName(any()) }
        Assert.assertTrue(isExceptionThrown)
    }


    @Test
    fun `getPokemonEggGroup success and return value`() = runTest {
        //given
        coEvery { api.getPokemonEggGroup(any()) } returns DUMMY_POKEMON_EGG
        //when
        val results = sut.getPokemonEggGroup("a")
        //then
        coVerify { api.getPokemonEggGroup(any()) }
        Assert.assertEquals(DUMMY_POKEMON_EGG.eggGroupSpecies, results)
        Assert.assertEquals(DUMMY_POKEMON_EGG.eggGroupName, "a")
        Assert.assertEquals(DUMMY_POKEMON_EGG.eggGroupSpecies[0].name, "a")
    }

    @Test
    fun `getPokemonEggGroup failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemonEggGroup(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getPokemonEggGroup("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemonEggGroup(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

    //=

    @Test
    fun `getPokemonEvolution success and return value`() = runTest {
        //given
        coEvery { api.getPokemonEvolution(any()) } returns DUMMY_POKEMON_EVOLUTION
        //when
        val results = sut.getPokemonEvolution("a")
        //then
        coVerify { api.getPokemonEvolution(any()) }
        Assert.assertEquals(DUMMY_POKEMON_EVOLUTION.evolutionChain.evolveTo.first().evolvingPokemonSpecies, results)
    }

    @Test
    fun `getPokemonEvolution failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { api.getPokemonEvolution(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getPokemonEvolution("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { api.getPokemonEvolution(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

    //=
    @Test
    fun `getDetailPokemonCharacteristic success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonCharacteristic(any())) } returns ApiResult.Success(
            DUMMY_POKEMON_CHARACTERISTIC
        )
        //when
        val results = sut.getDetailPokemonCharacteristic(1) as DataSourceResult.SourceValue
        //then
        coVerify { api.getPokemonCharacteristic(any()) }
        Assert.assertEquals(DUMMY_POKEMON_CHARACTERISTIC.descriptions[0].description, results.data)
    }

    @Test
    fun `getDetailPokemonCharacteristic failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonCharacteristic(any()) } throws IOException(NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonCharacteristic(any())) } returns ApiResult.Error(
            Exception(NETWORK_ERROR)
        )
        //when
        val results = sut.getDetailPokemonCharacteristic(1) as DataSourceResult.SourceError
        //then
        coVerify { api.getPokemonCharacteristic(any()) }
        Assert.assertEquals(NETWORK_ERROR, results.exception.message)
    }


    @Test
    fun `getPokemonLocationAreas success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonLocationAreas(any())) } returns ApiResult.Success(
            DUMMY_LIST_POKEMON_AREA
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as DataSourceResult.SourceValue
        //then
        coVerify { api.getPokemonLocationAreas(any()) }
        Assert.assertEquals(DUMMY_LIST_POKEMON_AREA[0].area.name, results.data[0])
        Assert.assertEquals(DUMMY_LIST_POKEMON_AREA[1].area.name, results.data[1])
        Assert.assertEquals(DUMMY_LIST_POKEMON_AREA[2].area.name, results.data[2])
    }

    @Test
    fun `getPokemonLocationAreas failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonLocationAreas(any()) } throws IOException(NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonLocationAreas(any())) } returns ApiResult.Error(
            Exception(
                NETWORK_ERROR
            )
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as DataSourceResult.SourceError
        //then
        coVerify { api.getPokemonLocationAreas(any()) }
        Assert.assertEquals(NETWORK_ERROR, results.exception.message)
    }

    @Test
    fun `getPokemonLocationAreas failed when Api return empty list of data`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonLocationAreas(any())) } returns ApiResult.Success(
            listOf()
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as DataSourceResult.SourceError
        //then
        coVerify { api.getPokemonLocationAreas(any()) }
        Assert.assertEquals(EMPTY_DATA, results.exception.message)
    }

    @Test
    fun `getPokemonById success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonById(any())) } returns ApiResult.Success(
            DUMMY_POKEMON_DETAIL
        )
        //when
        val results = sut.getPokemonById(1) as DataSourceResult.SourceValue
        //then
        coVerify { api.getPokemonById(any()) }
        Assert.assertEquals(DUMMY_POKEMON_DETAIL, results.data)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonId, results.data.pokemonId)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonName, results.data.pokemonName)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonHeight, results.data.pokemonHeight)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonWeight, results.data.pokemonWeight)
    }

    @Test
    fun `getPokemonById failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonById(any()) } throws IOException(NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonById(any())) } returns ApiResult.Error(
            Exception(NETWORK_ERROR)
        )
        //when
        val results = sut.getPokemonById(1) as DataSourceResult.SourceError
        //then
        coVerify { api.getPokemonById(any()) }
        Assert.assertEquals(NETWORK_ERROR, results.exception.message)
    }

    @Test
    fun `getDetailSpeciesPokemon success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonSpecies(any())) } returns ApiResult.Success(
            DUMMY_POKEMON_SPECIES_DETAIL
        )
        //when
        val results = sut.getDetailSpeciesPokemon(1) as DataSourceResult.SourceValue
        //then
        coVerify { api.getPokemonSpecies(any()) }
        Assert.assertEquals(DUMMY_POKEMON_SPECIES_DETAIL, results.data)
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.pokemonCaptureRate,
            results.data.pokemonCaptureRate
        )
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.pokemonHappiness,
            results.data.pokemonHappiness
        )
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.pokemonColor?.pokemonColor,
            results.data.pokemonColor?.pokemonColor
        )
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.pokemonShape?.pokemonShape,
            results.data.pokemonShape?.pokemonShape
        )
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.pokemonGeneration?.pokemonGenerationLString,
            results.data.pokemonGeneration?.pokemonGenerationLString
        )

    }

    @Test
    fun `getDetailSpecies Pokemon failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonSpecies(any()) } throws IOException(NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonSpecies(any())) } returns ApiResult.Error(
            IOException(NETWORK_ERROR)
        )
        //when
        val results = sut.getDetailSpeciesPokemon(1) as DataSourceResult.SourceError
        //then
        coVerify { api.getPokemonSpecies(any()) }

        Assert.assertEquals(NETWORK_ERROR, results.exception.message)
    }
}