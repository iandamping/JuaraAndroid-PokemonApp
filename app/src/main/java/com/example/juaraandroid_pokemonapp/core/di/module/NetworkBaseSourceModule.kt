package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkBaseSourceModule {

    @Binds
    @Singleton
    fun bindsBaseSource(baseSource: BaseSourceImpl): BaseSource
}