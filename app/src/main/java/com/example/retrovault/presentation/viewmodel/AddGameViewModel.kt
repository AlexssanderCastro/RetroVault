package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AddGameForm(
    val name: String = "",
    val platform: String = "",
    val year: String = "",
    val rating: String = ""
)

class AddGameViewModel : ViewModel() {
    private val _form = MutableStateFlow(AddGameForm())
    val form: StateFlow<AddGameForm> = _form.asStateFlow()

    fun updateName(value: String) {
        _form.value = _form.value.copy(name = value)
    }

    fun updatePlatform(value: String) {
        _form.value = _form.value.copy(platform = value)
    }

    fun updateYear(value: String) {
        _form.value = _form.value.copy(year = value)
    }

    fun updateRating(value: String) {
        _form.value = _form.value.copy(rating = value)
    }
}

