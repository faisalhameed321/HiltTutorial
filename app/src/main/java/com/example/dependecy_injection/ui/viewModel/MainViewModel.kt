package com.example.dependecy_injection.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dependecy_injection.data.repository.MainRepository
import com.example.dependecy_injection.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: MainRepository) : ViewModel() {

    private var _imagesUiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val imagesUiState: StateFlow<UIState> = _imagesUiState


    fun fetchImages() = viewModelScope.launch {
        repository.getImages()
            .onStart {
                _imagesUiState.value = UIState.Loading
            }.collect {
                _imagesUiState.value = UIState.Success(it)
            }
    }

}