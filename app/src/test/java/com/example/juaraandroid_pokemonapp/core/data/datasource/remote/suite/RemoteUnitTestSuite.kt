package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.suite

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.PokemonRemoteDataSourceImplTest
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area.RemotePokemonAreaManagerImplTest
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic.RemotePokemonCharacteristicManagerImplTest
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup.RemotePokemonEggGroupManagerImplTest
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution.RemotePokemonEvolutionManagerImplTest
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon.RemotePokemonManagerImplTest
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species.RemotePokemonSpeciesManagerImplTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    RemotePokemonAreaManagerImplTest::class,
    RemotePokemonCharacteristicManagerImplTest::class,
    RemotePokemonEggGroupManagerImplTest::class,
    RemotePokemonEvolutionManagerImplTest::class,
    RemotePokemonManagerImplTest::class,
    RemotePokemonSpeciesManagerImplTest::class,
    PokemonRemoteDataSourceImplTest::class
)
class RemoteUnitTestSuite