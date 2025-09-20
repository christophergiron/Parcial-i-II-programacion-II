package com.example.parcialiiprogramacionii

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ListaTareasScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var Tareas by remember { mutableStateOf(listOf<Tarea>()) }

    LaunchedEffect(Unit) {
        db.collection("Lista_de_tareas").addSnapshotListener { value, _ ->
            if (value != null) {
                Tareas = value.documents.mapNotNull { it.toObject(Tarea::class.java) }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        Text("Tareas", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(Tareas) { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Nombre: ${tarea.Nombre}")
                        Text("Fecha: ${tarea.Fecha}")
                        Text("Responsable: ${tarea.Responsable}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("nuevaTarea") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Tarea")
        }
    }
}