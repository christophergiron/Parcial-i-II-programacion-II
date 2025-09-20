package com.example.parcialiiprogramacionii

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NuevaTareaScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()

    var Nombre by remember { mutableStateOf("") }
    var Fecha by remember { mutableStateOf("") }
    var Responsable by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Crear Nueva Tarea", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = Nombre,
            onValueChange = { Nombre = it },
            label = { Text("Tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = Fecha,
            onValueChange = { Fecha = it },
            label = { Text("Fecha: dd/mm/yy") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = Responsable,
            onValueChange = { Responsable = it },
            label = { Text("Nombre del responsable") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (Nombre.isNotBlank() && Fecha.isNotBlank() && Responsable.isNotBlank()) {
                    val nuevaTarea = Tarea(Nombre, Fecha, Responsable)
                    db.collection("lista_de_tareas")
                        .add(nuevaTarea)
                        .addOnSuccessListener {
                            mensaje = "Tarea guardada"
                            navController.popBackStack()
                        }
                        .addOnFailureListener { e ->
                            mensaje = "No se pudo guardar la tarea"
                        }
                } else {
                    mensaje = "Los campos son necesarios"
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (mensaje.isNotEmpty()) {
            Text(mensaje, color = MaterialTheme.colorScheme.primary)
        }
    }
}

