package com.gustavo.architectureapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.databinding.FragmentGameListBinding
import com.gustavo.architectureapp.data.games.GameItem
import com.gustavo.architectureapp.data.games.ViewState
import com.gustavo.architectureapp.utils.search.DebounceSearchListener
import com.gustavo.architectureapp.view.adapters.GameAdapter
import com.gustavo.architectureapp.viewmodel.GamesViewModel
import com.gustavo.architectureapp.utils.image.ImageLoader
import kotlinx.coroutines.cancel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameListFragment : Fragment() {

    private var platformId = 1

    private val DOWN_DIRECTION = 1

    private var searchQuery = ""

    private val imageLoader: ImageLoader by inject()

    private val gameAdapter: GameAdapter = GameAdapter(imageLoader)

    private val gamesViewModel: GamesViewModel by viewModel()

    private lateinit var loadingDialog: AlertDialog

    private lateinit var viewBinding: FragmentGameListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentGameListBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGamesRecyclerView()

        setupSearchBar()

        setupLoadingDialog()

        arguments?.let {
            platformId = it.getInt(getString(R.string.platformIdKey))
            gamesViewModel.getGames(platformId, searchQuery)
        }

        observeGamesViewModel()
    }

    private fun setupSearchBar() {
        viewBinding.searchBar.setOnQueryTextListener(
            DebounceSearchListener(
                500,
                ::searchGame,
                coroutineScope = requireActivity().lifecycleScope
            )
        )
    }

    private fun searchGame(query: String?) {
        query?.let {
            gamesViewModel.getGames(platformId, it, true)
        }
    }

    private fun setupLoadingDialog() {
        loadingDialog =
            AlertDialog.Builder(requireContext(), R.style.loadingDialog)
                .setView(R.layout.loading_view)
                .create()
    }

    private fun setupGamesRecyclerView() {
        viewBinding.gameListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gameAdapter
            addOnScrollListener(createScrollListener())
        }
    }

    private fun observeGamesViewModel() {
        gamesViewModel.getViewStateLiveDataValue().observe(viewLifecycleOwner, {
            viewStateHandler(it)
        })
    }

    private fun viewStateHandler(viewState: ViewState) {
        when (viewState) {
            is ViewState.Loading -> loadingViewStateUpdate(true)
            is ViewState.Search -> searchViewStateUpdate(viewState.data)
            is ViewState.Success -> successResponseViewStateUpdate(viewState.data)
        }
    }

    private fun loadingViewStateUpdate(isLoading: Boolean) {
        if (isLoading) loadingDialog.show() else loadingDialog.dismiss()
    }

    private fun searchViewStateUpdate(gameList: List<GameItem>) {
        loadingViewStateUpdate(false)
        gameAdapter.updateGameList(gameList)
        viewBinding.gameListRecyclerView.scrollToPosition(0)
    }

    private fun successResponseViewStateUpdate(gameList: List<GameItem>) {
        loadingViewStateUpdate(false)
        gameAdapter.addNewGameList(gameList)
    }

    private fun createScrollListener(): RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(DOWN_DIRECTION))
                    gamesViewModel.getNextPage(platformId)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}