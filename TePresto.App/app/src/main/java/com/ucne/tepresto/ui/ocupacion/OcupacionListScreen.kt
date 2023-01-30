package com.ucne.tepresto.ui.ocupacion

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.tepresto.data.local.OcupacionEntity
import com.ucne.tepresto.ui.theme.TePrestoTheme

@Composable
fun OcupacionListScreen(
    viewModel: OcupacionListViewModel = hiltViewModel(),
    onNavigateToOcupacion: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    OcupacionListaBody(uiState.ocupacionList, onNavigateToOcupacion)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionListaBody(
    ocupacionesList: List<OcupacionEntity>,
    onNavigateToOcupacion: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Ocupaciones List")
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                OcupacionesList(ocupacionesList) { ocupacionId ->
                    onNavigateToOcupacion(ocupacionId)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(  onClick = {   }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun OcupacionesList(
    ocupacionesList: List<OcupacionEntity>,
    onNavigateToOcupacion: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(ocupacionesList) { ocupacion ->
            OcupacionRow(ocupacion) { ocupacionId ->
                onNavigateToOcupacion(ocupacionId)
            }
        }
    }
}

@Composable
fun OcupacionRow(ocupacion: OcupacionEntity, onOcupacionClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                onClick = {
                    onOcupacionClick(ocupacion.ocupacionId ?: 0)
                    Log.i("parametro", ocupacion.toString())
                }
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = ocupacion.descripcion,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(4f)
            )
            Text(
                text = ocupacion.salario.toString(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(2f)
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            color = Color.LightGray
        )
    }
}

@Preview
@Composable
fun OcupacionListaBodyPreview() {
    TePrestoTheme {
        val ocupacionList = listOf(
            OcupacionEntity(
                ocupacionId = 1,
                descripcion = "Profesor",
                salario = 10000.0
            ),
            OcupacionEntity(
                ocupacionId = 2,
                descripcion = "Ingeniero",
                salario = 12000.0
            ),
            OcupacionEntity(
                ocupacionId = 1,
                descripcion = "Abogado",
                salario = 11000.0
            )
        )
        OcupacionListaBody(ocupacionList, {})
    }
}