package com.gustavo.architectureapp.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.data.model.GameDetails
import com.gustavo.architectureapp.data.model.GameImages
import com.gustavo.architectureapp.databinding.GameDetailsFragmentBinding
import com.gustavo.architectureapp.utils.image.ImageLoader
import com.gustavo.architectureapp.utils.viewstate.GameDetailsViewState
import com.gustavo.architectureapp.presentation.view.adapters.GameDetailsImageAdapter
import com.gustavo.architectureapp.presentation.viewmodel.GameDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetailsFragment : Fragment() {

    private val viewModel: GameDetailsViewModel by viewModel()

    private val imageLoader: ImageLoader by inject()

    private lateinit var imagesAdapter: GameDetailsImageAdapter

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

        imagesAdapter = GameDetailsImageAdapter(imageLoader, requireContext())

        setupGameScreenshotsRecyclerView()

        setupLoadingDialog()

        arguments?.let {
            with(viewModel) {
                getGameDetails(it.getInt("gameId"))
                getGameImages(it.getInt("gameId"))
            }
        }

        observeViewModel()
    }

    private fun setupGameScreenshotsRecyclerView() {
        viewBinding.gameDetailsGridImageContent.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                1,
                GridLayoutManager.HORIZONTAL,
                false
            )
            adapter = imagesAdapter
        }
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
            is GameDetailsViewState.ImagesSuccess -> successGameImagesFetch(viewState.data)
        }
    }

    private fun successGameImagesFetch(data: GameImages) {
        loadingViewStateUpdate(false)
        imagesAdapter.updateScreenshotList(data.images)

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
