package com.gustavo.architectureapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gustavo.architectureapp.databinding.GameDetailsFragmentBinding
import com.gustavo.architectureapp.view.viewmodel.GameDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetailsFragment : Fragment() {

    private val viewModel: GameDetailsViewModel by viewModel()

    private lateinit var viewBinding: GameDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = GameDetailsFragmentBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}