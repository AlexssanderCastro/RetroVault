package com.example.retrovault.presentation.viewmodel

sealed class FormEvent {
    data class ShowMessage(val message: String) : FormEvent()
    data object NavigateBack : FormEvent()
}

