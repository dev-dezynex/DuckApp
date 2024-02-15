package com.example.duckapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duckapp.data.Duck
import com.example.duckapp.data.DuckRepository
import com.example.duckapp.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuckViewModel @Inject constructor(
    private val duckRepository: DuckRepository
) : ViewModel() {

    private val _duck = MutableStateFlow(Duck("", ""))
    val duck = _duck.asStateFlow()
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()
    private val _isLoading = MutableStateFlow(false)
    init {
        viewModelScope.launch {
            _isLoading.emit(true)
            getDuck()
        }
    }

    suspend fun getDuck() {
            duckRepository.getDuck().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        println("Result Error")
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        println("Result Success")
                        result.data?.let { duck ->
                            _duck.update { duck }
                        }
                    }
                }
            }
    }
}