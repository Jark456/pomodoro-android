package com.example.pomodoroandroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut


@Composable
fun PomodoroTimerUI(
    timeLeft: String,
    isWorking: Boolean,
    isRunning: Boolean,
    isPaused: Boolean,
    isSessionFinished: Boolean,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onStopClick: () -> Unit,
   // onRestartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isWorking) Color(0xFFEF5350) else Color(0xFF42A5F5))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isWorking) "Työjakso" else "Tauko",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = timeLeft,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (!isRunning) {
            Button(onClick = onStartClick) {
                Text("Käynnistä")
            }
        } else {
            if (!isPaused) {
                Button(onClick = onPauseClick) {
                    Text("Tauko")
                }
            } else {
                Button(onClick = onResumeClick) {
                    Text("Jatka")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = isRunning || isPaused,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(onClick = onStopClick) {
                    Text("Lopeta", color = Color.Red)
                }
            }


        }
    }

}