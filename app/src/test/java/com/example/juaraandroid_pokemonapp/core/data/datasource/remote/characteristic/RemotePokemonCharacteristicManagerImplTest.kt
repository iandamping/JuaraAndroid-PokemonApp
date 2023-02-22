package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic

import com.example.juaraandroid_pokemonapp.DummyPokemon
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RemotePokemonCharacteristicManagerImplTest {
    private val api: ApiInterface = mockk()
    private val baseSource: BaseSource = mockk()
    private lateinit var sut: RemotePokemonCharacteristicManager

    @Before
    fun setup() {
        sut = RemotePokemonCharacteristicManagerImpl(baseSource, api)
    }


    //=
    @Test
    fun `getDetailPokemonCharacteristic success and return value`() = runTest {
        //given
        coEvery { baseSource.oneShotCalls(api.getPokemonCharacteristic(any())) } returns ApiResult.Success(
            DummyPokemon.DUMMY_POKEMON_CHARACTERISTIC
        )
        //when
        val results = sut.getDetailPokemonCharacteristic(1) as ApiResult.Success
        //then
        coVerify { api.getPokemonCharacteristic(any()) }
        Assert.assertEquals(
            DummyPokemon.DUMMY_POKEMON_CHARACTERISTIC.descriptions[0].description,
            results.data
        )
    }

    @Test
    fun `getDetailPokemonCharacteristic failed when Api Throw exception`() = runTest {
        //given
        coEvery { api.getPokemonCharacteristic(any()) } throws IOException(NetworkConstant.NETWORK_ERROR)
        coEvery { baseSource.oneShotCalls(api.getPokemonCharacteristic(any())) } returns ApiResult.Error(
            Exception(NetworkConstant.NETWORK_ERROR)
        )
        //when
        val results = sut.getDetailPokemonCharacteristic(1) as ApiResult.Error
        //then
        coVerify { api.getPokemonCharacteristic(any()) }
        Assert.assertEquals(NetworkConstant.NETWORK_ERROR, results.exception.message)
    }

}