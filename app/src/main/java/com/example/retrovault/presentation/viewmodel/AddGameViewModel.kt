package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrovault.data.repository.GameRepository
import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

data class AddGameForm(
    val name: String = "",
    val platform: String = "",
    val year: String = "",
    val genre: String = "",
    val developer: String = "",
    val rating: String = "",
    val notes: String = "",
    val favorito: Boolean = false,
    val zerado: Boolean = false,
    val naListaDeDesejos: Boolean = false,
    val horasJogadas: String = ""
)

data class AddGameUiState(
    val form: AddGameForm = AddGameForm(),
    val isSaving: Boolean = false,
    val isEditing: Boolean = false
)

class AddGameViewModel(
    private val repository: GameRepository,
    private val gameId: Long? = null
) : ViewModel() {
    private val _state = MutableStateFlow(AddGameUiState(isEditing = gameId != null))
    val state: StateFlow<AddGameUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<FormEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<FormEvent> = _events

    init {
        if (gameId != null) {
            loadGame(gameId)
        }
    }

    fun updateName(value: String) = updateForm { it.copy(name = value) }
    fun updatePlatform(value: String) = updateForm { it.copy(platform = value) }
    fun updateYear(value: String) = updateForm { it.copy(year = value) }
    fun updateGenre(value: String) = updateForm { it.copy(genre = value) }
    fun updateDeveloper(value: String) = updateForm { it.copy(developer = value) }
    fun updateRating(value: String) = updateForm { it.copy(rating = value) }
    fun updateNotes(value: String) = updateForm { it.copy(notes = value) }
    fun updateFavorito(value: Boolean) = updateForm { it.copy(favorito = value) }
    fun updateZerado(value: Boolean) = updateForm { it.copy(zerado = value) }
    fun updateDesejado(value: Boolean) = updateForm { it.copy(naListaDeDesejos = value) }
    fun updateHorasJogadas(value: String) = updateForm { it.copy(horasJogadas = value) }

    fun save() {
        viewModelScope.launch {
            val state = _state.value
            val validationError = validate(state.form)
            if (validationError != null) {
                _events.emit(FormEvent.ShowMessage(validationError))
                return@launch
            }

            _state.update { it.copy(isSaving = true) }
            val form = state.form
            val year = form.year.toInt()
            val rating = form.rating.toFloat()
            val now = System.currentTimeMillis()
            val horas = form.horasJogadas.toIntOrNull()

            val baseGame = if (gameId != null) {
                repository.getGameById(gameId)
            } else {
                null
            }

            val game = Game(
                id = baseGame?.id ?: 0L,
                name = form.name.trim(),
                platform = form.platform.trim(),
                year = year,
                genre = form.genre.trim(),
                developer = form.developer.trim(),
                rating = rating,
                notes = form.notes.trim(),
                createdAt = baseGame?.createdAt ?: now,
                favorito = form.favorito,
                zerado = form.zerado,
                naListaDeDesejos = form.naListaDeDesejos,
                horasJogadas = horas,
                dataConclusao = if (form.zerado) now else null
            )

            if (gameId == null) {
                repository.insertGame(game)
                _events.emit(FormEvent.ShowMessage("Jogo cadastrado com sucesso"))
            } else {
                repository.updateGame(game)
                _events.emit(FormEvent.ShowMessage("Jogo atualizado com sucesso"))
            }
            _events.emit(FormEvent.NavigateBack)
            _state.update { it.copy(isSaving = false) }
        }
    }

    private fun loadGame(id: Long) {
        viewModelScope.launch {
            val game = repository.getGameById(id)
            if (game == null) {
                _events.emit(FormEvent.ShowMessage("Jogo nao encontrado"))
                _events.emit(FormEvent.NavigateBack)
                return@launch
            }
            _state.update {
                it.copy(
                    form = AddGameForm(
                        name = game.name,
                        platform = game.platform,
                        year = game.year.toString(),
                        genre = game.genre,
                        developer = game.developer,
                        rating = game.rating.toString(),
                        notes = game.notes,
                        favorito = game.favorito,
                        zerado = game.zerado,
                        naListaDeDesejos = game.naListaDeDesejos,
                        horasJogadas = game.horasJogadas?.toString() ?: ""
                    )
                )
            }
        }
    }

    private fun updateForm(block: (AddGameForm) -> AddGameForm) {
        _state.update { it.copy(form = block(it.form)) }
    }

    private fun validate(form: AddGameForm): String? {
        if (form.name.isBlank()) return "Nome obrigatorio"
        if (form.platform.isBlank()) return "Plataforma obrigatoria"
        val year = form.year.toIntOrNull() ?: return "Ano invalido"
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if (year !in 1970..currentYear) return "Ano deve estar entre 1970 e $currentYear"
        val rating = form.rating.toFloatOrNull() ?: return "Nota invalida"
        if (rating !in 0f..10f) return "Nota deve estar entre 0 e 10"
        return null
    }
}
