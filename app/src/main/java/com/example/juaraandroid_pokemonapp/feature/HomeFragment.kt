package com.example.juaraandroid_pokemonapp.feature

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.di.module.CustomDialogQualifier
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.databinding.FragmentHomeBinding
import com.example.juaraandroid_pokemonapp.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapter.HomeAdapterListener {
    @Inject
    @CustomDialogQualifier
    lateinit var customDialog: AlertDialog
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater)
        with(binding){
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            pokemonViewModel = homeViewModel
            rvHome.adapter = HomeAdapter(this@HomeFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.state.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            if (it.failedMessage.isNotEmpty()){
                Snackbar.make(view,it.failedMessage,Snackbar.LENGTH_SHORT).show()
            }
            if (it.isLoading) {
                customDialog.show()
            } else customDialog.dismiss()
        }
    }

    override fun onClicked(data: PokemonDetail) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.pokemonId))
    }


}