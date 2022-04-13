package com.example.juaraandroid_pokemonapp

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.juaraandroid_pokemonapp.feature.HomeAdapter
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonStatState
import com.example.juaraandroid_pokemonapp.feature.state.HomeScreenState
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_SKILL_MONS
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_TYPE_MONS
import com.example.juaraandroid_pokemonapp.util.RecyclerHorizontalSnapHelper

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: HomeScreenState) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
    val adapter = recyclerView.adapter as HomeAdapter
    if (data.data.isNotEmpty()){
        adapter.submitList(data.data)
    }
    if (recyclerView.onFlingListener == null) {
        RecyclerHorizontalSnapHelper()
            .attachToRecyclerView(recyclerView)
    }
}


@BindingAdapter("pokemonImageUrl")
fun bindImage(imgView: ImageView, imgUrl: String) {
    if (imgUrl.isNotEmpty()) {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.placeholder_image)
            error(R.drawable.error_image)
        }
    }
}

@BindingAdapter("bindText")
fun bindText(txtView: TextView, text: String) {
    if (text.isNotEmpty()) {
        txtView.text = text
    }
}
@BindingAdapter("bindTextFromInt")
fun bindTextFromInt(txtView: TextView, text: Int) {
    txtView.text = text.toString()
}

@BindingAdapter("bindCustomVisibilityType")
fun bindCustomTypeVisibility(linearLayout: LinearLayout, text: String) {
    if (text == ONE_TYPE_MONS) {
        linearLayout.visibility = View.GONE
    } else {
        linearLayout.visibility = View.VISIBLE
    }
}
@BindingAdapter("bindCustomVisibilityAbility")
fun bindCustomAbilityVisibility(linearLayout: LinearLayout, text: String) {
    if (text == ONE_SKILL_MONS) {
        linearLayout.visibility = View.GONE
    } else {
        linearLayout.visibility = View.VISIBLE
    }
}
