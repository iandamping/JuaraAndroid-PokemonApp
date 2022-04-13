package com.example.juaraandroid_pokemonapp.feature.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.di.module.CustomDialogQualifier
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.databinding.FragmentDetailBinding
import com.example.juaraandroid_pokemonapp.launchAndCollectIn
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_SKILL_MONS
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_TYPE_MONS
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    @Inject
    @CustomDialogQualifier
    lateinit var customDialog: AlertDialog

    private val detailViewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding
    private var isFavorite: Boolean = false
    private var idForDeleteItem: Int? = null
    private var pokemonDetail: PokemonDetail? = null
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel.setSelectedPokemonId(args.selectedPokemonId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return checkNotNull(binding?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBookmarkState()
        detailViewModel.detailState.launchAndCollectIn(
            this.viewLifecycleOwner,
            Lifecycle.State.STARTED
        ) {
            if (it.data != null) {
                pokemonDetail = it.data
                binding?.initView(it.data)
            }
            if (it.failedMessage.isNotEmpty()) {
                Snackbar.make(view, it.failedMessage, Snackbar.LENGTH_SHORT).show()
            }
            if (it.isLoading) {
                customDialog.show()
            } else customDialog.dismiss()
        }
    }

    private fun FragmentDetailBinding.initView(data: PokemonDetail) {
        observeDatabase(data.pokemonName)
        bindImage(ivItemPokemonImage, data.pokemonImage)
        bindImage(ivItemSmallPokemonImage1, data.pokemonSmallImage1)
        bindImage(ivItemSmallPokemonImage2, data.pokemonSmallImage3)
        bindImage(ivItemSmallPokemonImage3, data.pokemonSmallImage2)
        bindImage(ivItemSmallPokemonImage4, data.pokemonSmallImage4)
        tvStatName0.text = data.pokemonStat0.name
        tvStatPoint0.text = data.pokemonStat0.point.toString()
        tvStatName1.text = data.pokemonStat1.name
        tvStatPoint1.text = data.pokemonStat1.point.toString()
        tvStatName2.text = data.pokemonStat2.name
        tvStatPoint2.text = data.pokemonStat2.point.toString()
        tvStatName3.text = data.pokemonStat3.name
        tvStatPoint3.text = data.pokemonStat3.point.toString()
        tvStatName4.text = data.pokemonStat4.name
        tvStatPoint4.text = data.pokemonStat4.point.toString()
        tvStatName5.text = data.pokemonStat5.name
        tvStatPoint5.text = data.pokemonStat5.point.toString()
        tvType0.text = data.pokemonType0
        if (data.pokemonType1 == ONE_TYPE_MONS) {
            llType1.visibility = View.GONE
        } else {
            llType1.visibility = View.VISIBLE
            tvType1.text = data.pokemonType1
        }

        tvAbility0.text = data.pokemonAbility1
        if (data.pokemonAbility2 == ONE_SKILL_MONS) {
            llAbilities1.visibility = View.GONE
        } else {
            llAbilities1.visibility = View.VISIBLE
            tvAbility1.text = data.pokemonAbility2
        }

        ivFavorite.setOnClickListener {
            if (isFavorite) {
                if (idForDeleteItem != null) {
                    detailViewModel.clearFavorite(checkNotNull(idForDeleteItem))
                }
            } else {
                if (pokemonDetail != null) {
                    detailViewModel.saveFavorite(checkNotNull(pokemonDetail))
                }
            }
        }
    }

    private fun bindImage(imgView: ImageView, imgUrl: String) {
        if (imgUrl.isNotEmpty()) {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            imgView.load(imgUri) {
                placeholder(R.drawable.placeholder_image)
                error(R.drawable.error_image)
            }
        }
    }

    private fun observeBookmarkState() {
        detailViewModel.bookmarkState.launchAndCollectIn(
            this.viewLifecycleOwner,
            Lifecycle.State.STARTED
        ) {
            isFavorite = it
            binding?.ivFavorite?.load(
                if (isFavorite)
                    ContextCompat.getDrawable(
                        this@DetailFragment.requireContext(),
                        R.drawable.ic_round_star_rate_24
                    )
                else ContextCompat.getDrawable(
                    this@DetailFragment.requireContext(),
                    R.drawable.ic_round_star_border_24
                )
            )

        }
    }

    private fun observeDatabase(pokemonName: String) {
        detailViewModel.detailState.launchAndCollectIn(
            this.viewLifecycleOwner,
            Lifecycle.State.STARTED
        ) {
            detailViewModel.listFavorite().collect { favData ->
                favData.firstOrNull { it.pokemonName == pokemonName }.let { singleData ->
                    idForDeleteItem = if (singleData != null) {
                        detailViewModel.setBookmarkState(singleData.pokemonName == pokemonName)
                        singleData.pokemonFavoriteId
                    } else {
                        detailViewModel.setBookmarkState(false)
                        null
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}