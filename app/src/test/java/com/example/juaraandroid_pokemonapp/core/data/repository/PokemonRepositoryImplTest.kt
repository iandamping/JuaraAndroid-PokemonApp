package com.example.juaraandroid_pokemonapp.core.data.repository

import com.example.juaraandroid_pokemonapp.DummyPokemon
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_DETAIL
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_MAIN_RESPONSE
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_QUIZ_ENTITY
import com.example.juaraandroid_pokemonapp.DummyPokemon.DUMMY_POKEMON_SPECIES_DETAIL
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.PokemonRemoteDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.PokemonCacheDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.NETWORK_ERROR
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToDetail
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToSpeciesDetail
import com.example.juaraandroid_pokemonapp.core.domain.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PokemonRepositoryImplTest {

    private val remoteDataSource: PokemonRemoteDataSource = mockk()
    private val cacheDataSource: PokemonCacheDataSource = mockk()
    private lateinit var sut: PokemonRepository


    @Before
    fun setUp() {
        sut = PokemonRepositoryImpl(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource
        )
    }

    @Test
    fun `getPokemon success and return value`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemon() } returns DataSourceResult.SourceValue(
            DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults
        )
        coEvery { remoteDataSource.getDetailPokemon(any()) } returns DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getPokemon() as DomainResult.Content
        //then
        coVerify { remoteDataSource.getPokemon() }
        coVerify(exactly = 6) { remoteDataSource.getDetailPokemon(any()) }
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.mapToDetail().pokemonId, results.data[0].pokemonId)
        Assert.assertEquals(
            DUMMY_POKEMON_DETAIL.mapToDetail().pokemonName,
            results.data[0].pokemonName
        )
        Assert.assertEquals(
            DUMMY_POKEMON_DETAIL.mapToDetail().pokemonHeight,
            results.data[0].pokemonHeight
        )
        Assert.assertEquals(
            DUMMY_POKEMON_DETAIL.mapToDetail().pokemonWeight,
            results.data[0].pokemonWeight
        )
    }

    @Test
    fun `getPokemon failed when source Throw exception`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemon() } returns DataSourceResult.SourceValue(
            DUMMY_POKEMON_MAIN_RESPONSE.pokemonResults
        )
        coEvery { remoteDataSource.getDetailPokemon(any()) } throws IOException(NETWORK_ERROR)
        //when
        val results = sut.getPokemon() as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemon() }
        Assert.assertEquals(NETWORK_ERROR, results.message)
    }

    @Test
    fun `getPokemon failed when source data is Empty`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemon() } returns DataSourceResult.SourceValue(emptyList())
        //when
        val results = sut.getPokemon() as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemon() }
        Assert.assertEquals(EMPTY_DATA, results.message)
    }

    @Test
    fun `getEvolvingPokemon success and return value`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEvolution(any()) } returns DummyPokemon.DUMMY_POKEMON_EVOLUTION.evolutionChain.evolveTo.first().evolvingPokemonSpecies

        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(listOf(DUMMY_POKEMON_QUIZ_ENTITY))
        //when
        val results = sut.getEvolvingPokemon("a") as DomainResult.Content
        //then
        coVerify { remoteDataSource.getPokemonEvolution(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        Assert.assertEquals(1, results.data.pokemonId)
        Assert.assertEquals("a", results.data.pokemonName)

    }

    @Test
    fun `getEvolvingPokemon failed when source Throw exception`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEvolution(any()) } throws IOException(NETWORK_ERROR)
        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(listOf(DUMMY_POKEMON_QUIZ_ENTITY))
        //when
        val results = sut.getEvolvingPokemon("a") as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemonEvolution(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        Assert.assertEquals(NETWORK_ERROR, results.message)
    }

    @Test
    fun `getEvolvingPokemon success return value from backup remote data`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEvolution(any()) } returns DummyPokemon.DUMMY_POKEMON_EVOLUTION.evolutionChain.evolveTo.first().evolvingPokemonSpecies
        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(emptyList())
        coEvery { remoteDataSource.getPokemonByName(any()) } returns DataSourceResult.SourceValue(
            DUMMY_POKEMON_DETAIL
        )
        //when
        val results = sut.getEvolvingPokemon("a") as DomainResult.Content
        //then
        coVerify { remoteDataSource.getPokemonEvolution(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        coVerify { remoteDataSource.getPokemonByName(any()) }

        Assert.assertEquals(1, results.data.pokemonId)
        Assert.assertEquals(1, results.data.pokemonHeight)
        Assert.assertEquals(1, results.data.pokemonWeight)
        Assert.assertEquals("A", results.data.pokemonName)
    }

    @Test
    fun `getEvolvingPokemon failed from all resource`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEvolution(any()) } returns DummyPokemon.DUMMY_POKEMON_EVOLUTION.evolutionChain.evolveTo.first().evolvingPokemonSpecies
        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(emptyList())
        coEvery { remoteDataSource.getPokemonByName(any()) } returns DataSourceResult.SourceError(
            Exception(EMPTY_DATA)
        )
        //when
        val results = sut.getEvolvingPokemon("a") as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemonEvolution(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        coVerify { remoteDataSource.getPokemonByName(any()) }

        Assert.assertEquals(EMPTY_DATA, results.message)
    }
    //=

    @Test
    fun `getSimilarEggGroupPokemon success and return value`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEggGroup(any()) } returns DummyPokemon.DUMMY_POKEMON_EGG.eggGroupSpecies

        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(listOf(DUMMY_POKEMON_QUIZ_ENTITY))
        //when
        val results = sut.getSimilarEggGroupPokemon("a") as DomainResult.Content
        //then
        coVerify { remoteDataSource.getPokemonEggGroup(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        Assert.assertEquals(1, results.data.first().pokemonId)
        Assert.assertEquals("a", results.data.first().pokemonName)

    }

    @Test
    fun `getSimilarEggGroupPokemon failed when source Throw exception`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEggGroup(any()) } throws IOException(NETWORK_ERROR)
        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(listOf(DUMMY_POKEMON_QUIZ_ENTITY))
        //when
        val results = sut.getSimilarEggGroupPokemon("a") as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemonEggGroup(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        Assert.assertEquals(NETWORK_ERROR, results.message)
    }

    @Test
    fun `getSimilarEggGroupPokemon success return value from backup remote data`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEggGroup(any()) } returns DummyPokemon.DUMMY_POKEMON_EGG.eggGroupSpecies
        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(emptyList())
        coEvery { remoteDataSource.getDetailPokemonDirectByName(any()) } returns DUMMY_POKEMON_DETAIL
        //when
        val results = sut.getSimilarEggGroupPokemon("a") as DomainResult.Content
        //then
        coVerify { remoteDataSource.getPokemonEggGroup(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        coVerify { remoteDataSource.getDetailPokemonDirectByName(any()) }

        Assert.assertEquals(1, results.data.first().pokemonId)
        Assert.assertEquals(1, results.data.first().pokemonHeight)
        Assert.assertEquals(1, results.data.first().pokemonWeight)
        Assert.assertEquals("A", results.data.first().pokemonName)
    }

    @Test
    fun `getSimilarEggGroupPokemon failed from all resource`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonEggGroup(any()) } returns DummyPokemon.DUMMY_POKEMON_EGG.eggGroupSpecies
        coEvery { cacheDataSource.getPokemonQuiz() } returns flowOf(emptyList())
        coEvery { remoteDataSource.getDetailPokemonDirectByName(any()) } throws Exception(EMPTY_DATA)
        //when
        val results = sut.getSimilarEggGroupPokemon("a") as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemonEggGroup(any()) }
        coVerify { cacheDataSource.getPokemonQuiz() }
        coVerify { remoteDataSource.getDetailPokemonDirectByName(any()) }

        Assert.assertEquals(EMPTY_DATA, results.message)
    }
    //=

    @Test
    fun `getDetailSpeciesPokemon success and return value`() = runTest {
        //given
        coEvery { remoteDataSource.getDetailSpeciesPokemon(any()) } returns DataSourceResult.SourceValue(
            DUMMY_POKEMON_SPECIES_DETAIL
        )
        //when
        val results = sut.getDetailSpeciesPokemon(1) as DomainResult.Content
        //then
        coVerify { remoteDataSource.getDetailSpeciesPokemon(any()) }
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.mapToSpeciesDetail().captureRate,
            results.data.captureRate
        )
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.mapToSpeciesDetail().color,
            results.data.color
        )
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.mapToSpeciesDetail().eggGroup1,
            results.data.eggGroup1
        )
        Assert.assertEquals(
            DUMMY_POKEMON_SPECIES_DETAIL.mapToSpeciesDetail().eggGroup2,
            results.data.eggGroup2
        )
    }

    @Test
    fun `getDetailSpeciesPokemon failed and return Error`() = runTest {
        //given
        coEvery { remoteDataSource.getDetailSpeciesPokemon(any()) } returns DataSourceResult.SourceError(
            Exception(NETWORK_ERROR)
        )
        //when
        val results = sut.getDetailSpeciesPokemon(1) as DomainResult.Error
        //then
        coVerify { remoteDataSource.getDetailSpeciesPokemon(any()) }
        Assert.assertEquals(
            "Application encounter unknown problem :  $NETWORK_ERROR",
            results.message
        )
    }


    @Test
    fun `getDetailPokemonCharacteristic success and return value`() = runTest {
        //given
        coEvery { remoteDataSource.getDetailPokemonCharacteristic(any()) } returns DataSourceResult.SourceValue(
            "a"
        )
        //when
        val results = sut.getDetailPokemonCharacteristic(1) as DomainResult.Content
        //then
        coVerify { remoteDataSource.getDetailPokemonCharacteristic(any()) }
        Assert.assertEquals("a", results.data)
    }

    @Test
    fun `getDetailPokemonCharacteristic failed and return Error`() = runTest {
        //given
        coEvery { remoteDataSource.getDetailPokemonCharacteristic(any()) } returns DataSourceResult.SourceError(
            Exception(NETWORK_ERROR)
        )
        //when
        val results = sut.getDetailPokemonCharacteristic(1) as DomainResult.Error
        //then
        coVerify { remoteDataSource.getDetailPokemonCharacteristic(any()) }
        Assert.assertEquals(
            "Application encounter unknown problem :  $NETWORK_ERROR",
            results.message
        )
    }

    @Test
    fun `getPokemonLocationAreas success and return value`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonLocationAreas(any()) } returns DataSourceResult.SourceValue(
            listOf("a", "a", "a")
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as DomainResult.Content
        //then
        coVerify { remoteDataSource.getPokemonLocationAreas(any()) }
        Assert.assertEquals(listOf("a", "a", "a"), results.data)
    }

    @Test
    fun `getPokemonLocationAreas failed and return Error`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonLocationAreas(any()) } returns DataSourceResult.SourceError(
            Exception(
                NETWORK_ERROR
            )
        )
        //when
        val results = sut.getPokemonLocationAreas(1) as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemonLocationAreas(any()) }
        Assert.assertEquals(
            "Application encounter unknown problem :  $NETWORK_ERROR",
            results.message
        )
    }


    @Test
    fun `getPokemonById success and return value`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonById(any()) } returns DataSourceResult.SourceValue(
            DUMMY_POKEMON_DETAIL
        )
        //when
        val results = sut.getPokemonById(1) as DomainResult.Content
        //then
        coVerify { remoteDataSource.getPokemonById(any()) }
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.mapToDetail(), results.data)
        Assert.assertEquals(DUMMY_POKEMON_DETAIL.mapToDetail().pokemonId, results.data.pokemonId)
        Assert.assertEquals(
            DUMMY_POKEMON_DETAIL.mapToDetail().pokemonWeight,
            results.data.pokemonWeight
        )
        Assert.assertEquals(
            DUMMY_POKEMON_DETAIL.mapToDetail().pokemonHeight,
            results.data.pokemonHeight
        )
        Assert.assertEquals(
            DUMMY_POKEMON_DETAIL.mapToDetail().pokemonName,
            results.data.pokemonName
        )
    }

    @Test
    fun `getPokemonById failed and return Error`() = runTest {
        //given
        coEvery { remoteDataSource.getPokemonById(any()) } returns DataSourceResult.SourceError(
            Exception(NETWORK_ERROR)
        )
        //when
        val results = sut.getPokemonById(1) as DomainResult.Error
        //then
        coVerify { remoteDataSource.getPokemonById(any()) }
        Assert.assertEquals(
            "Application encounter unknown problem :  $NETWORK_ERROR",
            results.message
        )
    }
}