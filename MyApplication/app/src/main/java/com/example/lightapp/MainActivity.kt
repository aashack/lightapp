package com.example.lightapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Keep screen on
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            LampApp()
        }
    }
}

@Composable
fun LampApp() {
    var brightness by remember { mutableStateOf(1f) }
    var selectedColor by remember { mutableStateOf(Color.White) }

    // Define color options
    val colorOptions = listOf(
        "White" to Color.White,
        "Red" to Color.Red,
        "Green" to Color.Green,
        "Blue" to Color.Blue,
        "Yellow" to Color.Yellow,
        "Purple" to Color(0xFF800080) // Purple color
    )

    // Calculate the display color with brightness applied
    val displayColor = selectedColor.copy(
        red = selectedColor.red * brightness,
        green = selectedColor.green * brightness,
        blue = selectedColor.blue * brightness
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(displayColor)
    ) {
        // Centered control panel
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Brightness control
            Slider(
                value = brightness,
                onValueChange = { brightness = it },
                modifier = Modifier
                    .width(280.dp)
                    .padding(vertical = 16.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Radio buttons for colors in horizontal layout
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                colorOptions.forEach { (_, color) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .selectable(
                                selected = selectedColor == color,
                                onClick = { selectedColor = color }
                            )
                    ) {
                        RadioButton(
                            selected = selectedColor == color,
                            onClick = { selectedColor = color },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.Gray
                            )
                        )

                        // Color preview circle below radio button
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }
        }
    }
}