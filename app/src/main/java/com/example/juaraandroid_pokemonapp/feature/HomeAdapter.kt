package com.example.juaraandroid_pokemonapp.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.databinding.ItemHomeBinding

class HomeAdapter(private val listener: HomeAdapterListener) :
    ListAdapter<PokemonDetail, HomeViewHolder>(DiffCallback) {

    interface HomeAdapterListener {
        fun onClicked(data: PokemonDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClicked(data)
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<PokemonDetail>() {
        override fun areItemsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
            return oldItem.pokemonName == newItem.pokemonName
        }

        override fun areContentsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
            return oldItem.pokemonImage == newItem.pokemonImage
        }
    }
}