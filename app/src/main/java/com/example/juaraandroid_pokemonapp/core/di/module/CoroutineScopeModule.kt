package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.di.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Singleton
    @Provides
    @ApplicationDefaultScope
    fun providesApplicationDefaultScope(
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Singleton
    @Provides
    @ApplicationIoScope
    fun providesApplicationIoScope(
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Singleton
    @Provides
    @ApplicationMainScope
    fun providesApplicationMainScope(
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}