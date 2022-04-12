package com.example.juaraandroid_pokemonapp.core.domain.model

sealed class UiState<out T> {
    data class Content<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}