package com.example.juaraandroid_pokemonapp.core.data.datasource.remote

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
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area.RemotePokemonAreaManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic.RemotePokemonCharacteristicManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup.RemotePokemonEggGroupManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution.RemotePokemonEvolutionManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon.RemotePokemonManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.NETWORK_ERROR
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species.RemotePokemonSpeciesManager
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

    private val areaManager: RemotePokemonAreaManager = mockk()
    private val characteristicManager: RemotePokemonCharacteristicManager = mockk()
    private val eggGroupManager: RemotePokemonEggGroupManager = mockk()
    private val evolutionManager: RemotePokemonEvolutionManager = mockk()
    private val pokemonManager: RemotePokemonManager = mockk()
    private val speciesManager: RemotePokemonSpeciesManager = mockk()
    private lateinit var sut: PokemonRemoteDataSource

    @Before
    fun setup() {
        sut = PokemonRemoteDataSourceImpl(
            speciesManager = speciesManager,
            areaManager = areaManager,
            characteristicManager = characteristicManager,
            eggGroupManager = eggGroupManager,
            pokemonManager = pokemonManager,
            evolutionManager = evolutionManager
        )
    }

    @Test
    fun `getPokemon success and return value`() = runTest {
        //given
        coEvery { pokemonManager.getPokemon() } returns ApiResult.Success(
            DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults
        )
        //when
        val results = sut.getPokemon() as DataSourceResult.SourceValue
        //then
        coVerify { pokemonManager.getPokemon() }
        Assert.assertEquals(DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults, results.data)
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_1, results.data[0])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_2, results.data[1])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_3, results.data[2])
    }


    @Test
    fun `getPaginationPokemon success and return value`() = runTest {
        //given
        coEvery { pokemonManager.getPaginationPokemon(any()) } returns ApiResult.Success(
            DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults
        )
        //when
        val results = sut.getPaginationPokemon(2) as DataSourceResult.SourceValue
        //then
        coVerify { pokemonManager.getPaginationPokemon(any()) }
        Assert.assertEquals(DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults, results.data)
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_1, results.data[0])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_2, results.data[1])
        Assert.assertEquals(DUMMY_URL_POKEMON_RESULTS_3, results.data[2])
    }

    @Test
    fun `getDetailPokemon success and return value`() = runTest {
        //given
        coEvery { pokemonManager.getDetailPokemon(any()) } returns DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getDetailPokemon("a")
        //then
        coVerify { pokemonManager.getDetailPokemon(any()) }
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
        coEvery { pokemonManager.getDetailPokemon(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getDetailPokemon("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { pokemonManager.getDetailPokemon(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

    @Test
    fun `getDetailPokemonDirectByName success and return value`() = runTest {
        //given
        coEvery { pokemonManager.getDetailPokemonDirectByName(any()) } returns DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getDetailPokemonDirectByName("a")
        //then
        coVerify { pokemonManager.getDetailPokemonDirectByName(any()) }
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
        coEvery { pokemonManager.getDetailPokemonDirectByName(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getDetailPokemonDirectByName("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { pokemonManager.getDetailPokemonDirectByName(any()) }
        Assert.assertTrue(isExceptionThrown)
    }


    @Test
    fun `getPokemonEggGroup success and return value`() = runTest {
        //given
        coEvery { eggGroupManager.getPokemonEggGroup(any()) } returns DUMMY_POKEMON_EGG.eggGroupSpecies
        //when
        val results = sut.getPokemonEggGroup("a")
        //then
        coVerify { eggGroupManager.getPokemonEggGroup(any()) }
        Assert.assertEquals(DUMMY_POKEMON_EGG.eggGroupSpecies, results)
        Assert.assertEquals(DUMMY_POKEMON_EGG.eggGroupName, "a")
        Assert.assertEquals(DUMMY_POKEMON_EGG.eggGroupSpecies[0].name, "a")
    }

    @Test
    fun `getPokemonEggGroup failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { eggGroupManager.getPokemonEggGroup(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getPokemonEggGroup("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { eggGroupManager.getPokemonEggGroup(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

    //=

    @Test
    fun `getPokemonEvolution success and return value`() = runTest {
        //given
        coEvery { evolutionManager.getPokemonEvolution(any()) } returns DUMMY_POKEMON_EVOLUTION.evolutionChain.evolveTo.first().evolvingPokemonSpecies
        //when
        val results = sut.getPokemonEvolution("a")
        //then
        coVerify { evolutionManager.getPokemonEvolution(any()) }
        Assert.assertEquals(
            DUMMY_POKEMON_EVOLUTION.evolutionChain.evolveTo.first().evolvingPokemonSpecies,
            results
        )
    }

    @Test
    fun `getPokemonEvolution failed when Api Throw exception`() = runTest {
        var isExceptionThrown = false
        //given
        coEvery { evolutionManager.getPokemonEvolution(any()) } throws IOException(NETWORK_ERROR)
        //when
        try {
            sut.getPokemonEvolution("a")
        } catch (e: Exception) {
            isExceptionThrown = true
            Assert.assertEquals(e.message, NETWORK_ERROR)
        }
        //then
        coVerify { evolutionManager.getPokemonEvolution(any()) }
        Assert.assertTrue(isExceptionThrown)
    }

    @Test
    fun `getDetailPokemonCharacteristic success and return value`() = runTest {
        //given
        coEvery { characteristicManager.getDetailPokemonCharacteristic(any()) } returns ApiResult.Success(
            DUMMY_POKEMON_CHARACTERISTIC.descriptions[0].description
        )
        //when
        val results = sut.getDetailPokemonCharacteristic(1) as DataSourceResult.SourceValue
        //then
        coVerify { characteristicManager.getDetailPokemonCharacteristic(any()) }
        Assert.assertEquals(DUMMY_POKEMON_CHARACTERISTIC.descriptions[0].description, results.data)
    }


    @Test
    fun `getPokemonLocationAreas success and return value`() = runTest {
        //given
        coEvery { areaManager.getPokemonLocationAreas(any()) } returns ApiResult.Success(
            DUMMY_LIST_POKEMON_AREA.map { it.area.name }
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as DataSourceResult.SourceValue
        //then
        coVerify { areaManager.getPokemonLocationAreas(any()) }
        Assert.assertEquals(DUMMY_LIST_POKEMON_AREA[0].area.name, results.data[0])
        Assert.assertEquals(DUMMY_LIST_POKEMON_AREA[1].area.name, results.data[1])
        Assert.assertEquals(DUMMY_LIST_POKEMON_AREA[2].area.name, results.data[2])
    }

    @Test
    fun `getPokemonById success and return value`() = runTest {
        //given
        coEvery { pokemonManager.getPokemonById(any()) } returns ApiResult.Success(
            DUMMY_POKEMON_DETAIL
        )
        //when
        val results = sut.getPokemonById(1) as DataSourceResult.SourceValue
        //then
        coVerify { pokemonManager.getPokemonById(any()) }
        Assert.assertEquals(DUMMY_POKEMON_DETAIL, results.data)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonId, results.data.pokemonId)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonName, results.data.pokemonName)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonHeight, results.data.pokemonHeight)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.pokemonWeight, results.data.pokemonWeight)
    }

    @Test
    fun `getDetailSpeciesPokemon success and return value`() = runTest {
        //given
        coEvery { speciesManager.getDetailSpeciesPokemon(any()) } returns ApiResult.Success(
            DUMMY_POKEMON_SPECIES_DETAIL
        )
        //when
        val results = sut.getDetailSpeciesPokemon(1) as DataSourceResult.SourceValue
        //then
        coVerify { speciesManager.getDetailSpeciesPokemon(any()) }
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
}