package com.gustavo.architectureapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gustavo.architectureapp.databinding.FragmentPlatformListBinding
import com.gustavo.architectureapp.view.adapters.PlatformAdapter
import org.koin.android.ext.android.inject

class PlatformListFragment : Fragment() { // view state

    private val platformAdapter: PlatformAdapter by inject()

    private lateinit var viewBinding: FragmentPlatformListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentPlatformListBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPlatformRecyclerView()

    }

    private fun setupPlatformRecyclerView() {
        viewBinding.platformsRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),  2)
            adapter = platformAdapter
        }
    }

}