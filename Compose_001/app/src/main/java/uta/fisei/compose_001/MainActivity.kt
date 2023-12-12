package uta.fisei.compose_001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import uta.fisei.compose_001.ui.theme.Compose_001Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_001Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    //MyText()
                    MyTextTwo()
                    //MyTextTest()
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_001Theme {
        Greeting("Android")
    }
}*/


@Composable
fun MyText(name: String) {
    Text(text = "Sexto: $name")
}

@Preview(
    name = "UNO", showBackground = true,
    heightDp = 60, widthDp = 100,
    showSystemUi = true,
    apiLevel = 34,
    locale = "fr-rFR",
    device = Devices.NEXUS_5,
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE,
    group = "MyGroup"
)
@Composable
fun MyTextTest() {
    MyText(name = "TI")
}

@Preview(name = "DOS", showBackground = true)
@Composable
fun MyTextTwo() {
    Text(
        text = "Tecnologias de la Informacion",
        Modifier
            //esto significa el wrap content
            .fillMaxSize()
            .padding(all = 60.dp)
            .clickable {  }
    )
}

@Preview(name = "TRES", showBackground = true)
@Composable
fun MyTextThree() {
    Text(text = "FISEI")
}