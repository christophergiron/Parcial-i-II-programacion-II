package com.example.parcialiiprogramacionii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcialiiprogramacionii.ui.theme.ParcialIIProgramacionIITheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (FirebaseApp.getApps(this).isEmpty()){
            FirebaseApp.initializeApp(this)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialIIProgramacionIITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainScreens()
                    }
                }
            }
        }
    }
}

data class Tarea(
    val Nombre: String = "",
    val Fecha: String = "",
    val Responsable: String = ""
)

@Composable
fun MainScreens() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listaTareas") {
        composable("listaTareas") { ListaTareasScreen(navController) }

        composable("nuevaTarea") { NuevaTareaScreen(navController) }
    }
}

