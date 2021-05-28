package com.gustavo.architectureapp.di

import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.view.adapters.PlatformAdapter
import com.gustavo.architectureapp.data.api.GamesApiServiceProvider
import com.gustavo.architectureapp.data.enums.PlatformEnum.*
import com.gustavo.architectureapp.data.model.PlatformGrid
import com.gustavo.architectureapp.data.interactor.GameDetailsInteractor
import com.gustavo.architectureapp.data.mapper.GameMapper
import com.gustavo.architectureapp.data.interactor.GamesInteractor
import com.gustavo.architectureapp.data.mapper.GameDetailsMapper
import com.gustavo.architectureapp.data.mapper.GameListingMapper
import com.gustavo.architectureapp.data.repository.GameRepository
import com.gustavo.architectureapp.utils.pagination.PaginationController
import com.gustavo.architectureapp.utils.provider.ThreadContextProvider
import com.gustavo.architectureapp.view.adapters.GameAdapter
import com.gustavo.architectureapp.viewmodel.GameDetailsViewModel
import com.gustavo.architectureapp.viewmodel.GamesViewModel
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

    factory { PlatformAdapter(
            arrayListOf(
                    xboxOnePlatformInput(),
                    ps4PlatformInput(),
                    xboxSeriesPlatformInput(),
                    ps5PlatformInput(),
                    pcPlatformInput(),
                    nintendoSwitchPlatformInput()
            )
    ) }

}

fun nintendoSwitchPlatformInput() = PlatformGrid(
        imageResourceId = R.drawable.nintendo_switch_platform_icon ,
        platformId = NINTENDO_SWITCH.platformId
)

fun pcPlatformInput() = PlatformGrid(
        imageResourceId = R.drawable.pc_platform_icon,
        platformId = PC.platformId
)

fun ps5PlatformInput() = PlatformGrid(
        imageResourceId = R.drawable.ps5_platform_icon,
        platformId = PS5.platformId
)

fun xboxSeriesPlatformInput() = PlatformGrid(
        imageResourceId = R.drawable.xbox_series_platform_icon,
        platformId = XBOX_SERIES.platformId
)

fun ps4PlatformInput() = PlatformGrid(
        imageResourceId = R.drawable.ps4_platform_icon,
        platformId = PS4.platformId
)

fun xboxOnePlatformInput() = PlatformGrid(
        imageResourceId = R.drawable.xbox_one_platform_icon,
        platformId = XBOX_ONE.platformId
)
