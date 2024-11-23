package com.booktrace.booktrace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.booktrace.booktrace.ui.navigation.SetupNavGraph
import com.booktrace.booktrace.ui.theme.BooktraceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: viewModel = viewModel()
        setContent {
            BooktraceTheme {
               Inicio()
            }
        }
    }
}

@Composable
fun Bienvenida(modifier: Modifier = Modifier) {
    Text(
        text = "Bienvenido a Booktrace!",
        modifier = modifier
    )
}

@Composable
fun Inicio() {
    val navController = rememberNavController()
    SetupNavGraph(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun BienvenidaPreview() {
    BooktraceTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Bienvenida()
        }
    }
}