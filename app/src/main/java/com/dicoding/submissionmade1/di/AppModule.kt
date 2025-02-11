package com.dicoding.submissionmade1.di

import com.dicoding.submissionmade1.core.domain.usecase.DataStoreInteractor
import com.dicoding.submissionmade1.core.domain.usecase.DataStoreUseCase
import com.dicoding.submissionmade1.core.domain.usecase.RemoteInteractor
import com.dicoding.submissionmade1.core.domain.usecase.RemoteUseCase
import com.dicoding.submissionmade1.core.domain.usecase.RoomInteractor
import com.dicoding.submissionmade1.core.domain.usecase.RoomUseCase
import com.dicoding.submissionmade1.ui.detail.DetailActivityViewModel
import com.dicoding.submissionmade1.ui.finished.FinishedViewModel
import com.dicoding.submissionmade1.ui.home.HomeViewModel
import com.dicoding.submissionmade1.ui.search.SearchViewModel
import com.dicoding.submissionmade1.ui.upcoming.UpcomingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<RemoteUseCase> { RemoteInteractor(get()) }
    factory<RoomUseCase> { RoomInteractor(get()) }
    factory<DataStoreUseCase> { DataStoreInteractor(get()) }
}
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailActivityViewModel(get(), get()) }
    viewModel { FinishedViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { UpcomingViewModel(get()) }
}