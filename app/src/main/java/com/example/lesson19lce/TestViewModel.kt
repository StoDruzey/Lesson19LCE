package com.example.lesson19lce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    private val _lceFlow = MutableSharedFlow<Lce<String>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val lceFlow: Flow<Lce<String>> = _lceFlow.asSharedFlow()

    fun onButtonClicked() {
        viewModelScope.launch {
            _lceFlow.tryEmit(Lce.Loading)
            delay(5000)
            _lceFlow.tryEmit(Lce.Content("Success"))
        }
    }
}