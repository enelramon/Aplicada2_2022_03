package com.ucne.tepresto.ui.ocupacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.tepresto.data.local.OcupacionEntity
import com.ucne.tepresto.data.repository.OcupacionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OcupacionListUiState(
    val ocupacionList: List<OcupacionEntity> = emptyList()
)

@HiltViewModel
class OcupacionListViewModel @Inject constructor(
    private val repository: OcupacionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OcupacionListUiState())
    val uiState: StateFlow<OcupacionListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getList().collect() { list ->
                _uiState.update { it.copy(ocupacionList = list) }
            }
        }
    }
}