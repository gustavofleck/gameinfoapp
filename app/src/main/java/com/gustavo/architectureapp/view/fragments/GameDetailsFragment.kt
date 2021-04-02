package com.gustavo.architectureapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.data.games.GameDetails
import com.gustavo.architectureapp.databinding.GameDetailsFragmentBinding
import com.gustavo.architectureapp.utils.image.ImageLoader
import com.gustavo.architectureapp.utils.viewstate.GameDetailsViewState
import com.gustavo.architectureapp.viewmodel.GameDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetailsFragment : Fragment() {

    private val viewModel: GameDetailsViewModel by viewModel()

    private val imageLoader: ImageLoader by inject()

    private lateinit var loadingDialog: AlertDialog

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

        arguments?.let {
            viewModel.getGameDetails(it.getInt("gameId"))
        }

        observeViewModel()

        setupLoadingDialog()

    }

    private fun setupLoadingDialog() {
        loadingDialog =
            AlertDialog.Builder(requireContext(), R.style.loadingDialog)
                .setView(R.layout.loading_view)
                .create()
    }

    private fun observeViewModel() {
        viewModel.getViewStateLiveData().observe(viewLifecycleOwner, Observer {
            viewStateHandler(it)
        })
    }

    private fun viewStateHandler(viewState: GameDetailsViewState) {
        when(viewState) {
            is GameDetailsViewState.Loading -> loadingViewStateUpdate(true)
            is GameDetailsViewState.Success -> successResponseViewStateUpdate(viewState.data)
        }
    }

    private fun successResponseViewStateUpdate(data: GameDetails) {
        loadingViewStateUpdate(false)
        with(viewBinding) {
            with(data) {
                gameDetailsNameTextView.text = name
                gameDetailsDescription.text = description
                gameDetailsReleaseDateData.text = released
                gameDetailsMetacriticData.text = metacritic
                imageLoader.loadSquareImage(gameBackgroundImageView, backgroundImageUri)
            }
        }
    }

    private fun loadingViewStateUpdate(isLoading: Boolean) {
        if (isLoading) loadingDialog.show() else loadingDialog.dismiss()
    }
}