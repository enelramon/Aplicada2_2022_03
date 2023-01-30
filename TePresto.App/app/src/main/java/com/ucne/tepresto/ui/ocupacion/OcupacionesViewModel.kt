package com.ucne.tepresto.ui.ocupacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.tepresto.data.local.OcupacionEntity
import com.ucne.tepresto.data.repository.OcupacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OcupacionUiState(
    val ocupacionesList: List<OcupacionEntity> = emptyList()
)

class OcupacionesViewModel @Inject constructor(
    private  val repository: OcupacionRepository
    ) : ViewModel() {

    var uiState = MutableStateFlow(OcupacionUiState())
        private set

    init {
        viewModelScope.launch {
            repository.getList().collect() { list ->
                uiState.update {
                    it.copy(ocupacionesList = list)
                }
            }
        }
    }

     fun save(){
         //recordar validar antes
        viewModelScope.launch {
            repository.insert(OcupacionEntity(descripcion =  "Ocupacion 1", salario = 1000.0))
        }
    }

}