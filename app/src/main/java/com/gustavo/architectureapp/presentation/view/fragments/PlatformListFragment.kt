package com.gustavo.architectureapp.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gustavo.architectureapp.presentation.view.composeui.PlatformListCompose

class PlatformListFragment : Fragment() {

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PlatformListCompose().PlatformGrid(::navigateToGameList)
            }
        }
    }

    private fun navigateToGameList(platformId: Int) {
        findNavController().navigate(
            PlatformListFragmentDirections.actionToGameListFragment(platformId)
        )
    }
}