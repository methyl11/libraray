package com.x3lnthpi.library

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _selectedImageResource = MutableStateFlow<Int?>(null)
    val selectedImageResource: StateFlow<Int?> = _selectedImageResource

    fun setSelectedImageResource(imageResource: Int) {
        _selectedImageResource.value = imageResource
    }
}