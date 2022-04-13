package com.example.juaraandroid_pokemonapp.feature

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juaraandroid_pokemonapp.R
import com.example.juaraandroid_pokemonapp.core.di.module.CustomDialogQualifier
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.databinding.FragmentHomeBinding
import com.example.juaraandroid_pokemonapp.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapter.HomeAdapterListener {
    @Inject
    @CustomDialogQualifier
    lateinit var customDialog: AlertDialog
    private val homeViewModel: HomeViewModel by viewModels()
    private var isLinearLayoutManager = true
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater)
        with(binding) {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            pokemonViewModel = homeViewModel
            rvHome.adapter = HomeAdapter(this@HomeFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvHome)
        homeViewModel.state.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            if (it.failedMessage.isNotEmpty()) {
                Snackbar.make(view, it.failedMessage, Snackbar.LENGTH_SHORT).show()
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