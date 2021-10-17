package com.gustavo.architectureapp.di

import com.gustavo.architectureapp.data.api.GamesApiServiceProvider
import com.gustavo.architectureapp.data.interactor.GameDetailsInteractor
import com.gustavo.architectureapp.data.mapper.GameMapper
import com.gustavo.architectureapp.data.interactor.GamesInteractor
import com.gustavo.architectureapp.data.mapper.GameDetailsMapper
import com.gustavo.architectureapp.data.mapper.GameListingMapper
import com.gustavo.architectureapp.data.repository.GameRepository
import com.gustavo.architectureapp.utils.pagination.PaginationController
import com.gustavo.architectureapp.utils.provider.ThreadContextProvider
import com.gustavo.architectureapp.presentation.view.adapters.GameAdapter
import com.gustavo.architectureapp.presentation.viewmodel.GameDetailsViewModel
import com.gustavo.architectureapp.presentation.viewmodel.GamesViewModel
import com.gustavo.architectureapp.utils.image.ImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gamesModule = module {

    single { GamesApiServiceProvider().retrofitInstance() }

    factory { GameMapper() }

    factory { GameListingMapper(gameMapper = get()) }

    factory { GameDetailsMapper() }

    factory { GameRepository(
        gamesApi = get(),
        gameListingMapper = get(),
        gameDetailsMapper = get()
    ) }

    factory { GamesInteractor( gameRepository = get()) }

    factory { GameDetailsInteractor( gameRepository = get()) }

    factory { PaginationController() }

    factory { ThreadContextProvider() }

    viewModel { GamesViewModel(
        gamesInteractor = get(),
        paginationController = get(),
        threadContextProvider = get()
    ) }

    viewModel { GameDetailsViewModel(
        gameDetailsInteractor = get(),
        threadContextProvider = get()
    ) }

    factory { ImageLoader(androidContext()) }

    factory { GameAdapter(imageLoader = get()) }
}