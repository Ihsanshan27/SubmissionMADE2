package com.dicoding.submissionmade1.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionmade1.core.domain.model.Favorite
import com.dicoding.submissionmade1.core.domain.usecase.RoomUseCase
import com.dicoding.submissionmade1.core.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavoriteViewModel(private val roomUseCase: RoomUseCase) : ViewModel() {
    private val _listFavorite = MutableStateFlow<ResultState<List<Favorite>>>(ResultState.Loading)
    val listFavorite: StateFlow<ResultState<List<Favorite>>> = _listFavorite

    fun getAllFavorite() {
        viewModelScope.launch {
            roomUseCase.getAllFavorites()
                .onStart { _listFavorite.value = ResultState.Loading }
                .catch { _listFavorite.value = ResultState.Error(it.message.toString()) }
                .collect {
                    _listFavorite.value = ResultState.Success(it)
                }
        }
    }
}