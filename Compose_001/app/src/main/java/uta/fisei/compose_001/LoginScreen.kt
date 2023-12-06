package uta.fisei.compose_001

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Beinvenido a Jetpack Compose 1.0",
        modifier = modifier
    )
}