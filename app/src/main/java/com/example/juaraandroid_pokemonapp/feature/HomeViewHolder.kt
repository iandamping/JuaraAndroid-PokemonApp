package com.example.juaraandroid_pokemonapp.feature

import androidx.recyclerview.widget.RecyclerView
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.databinding.ItemHomeBinding

class HomeViewHolder(
    private val binding: ItemHomeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PokemonDetail) {
        binding.pokemonDetail = item
        binding.executePendingBindings()
    }

}