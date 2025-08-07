package com.example.pomodoroandroid

import android.os.Bundle
import android.media.MediaPlayer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.pomodoroandroid.ui.PomodoroTimerUI
import com.example.pomodoroandroid.viewmodel.TimerViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current

            val timeLeft by viewModel.timeLeft.collectAsState()
            val isWorking by viewModel.isWorking.collectAsState()
            val isRunning by viewModel.isRunning.collectAsState()
            val isPaused by viewModel.isPaused.collectAsState()
            val sessionEnded by viewModel.isSessionFinished.collectAsState()
            val isSessionFinished = viewModel.isSessionFinished.collectAsState().value

            PomodoroTimerUI(
                timeLeft = timeLeft,
                isWorking = isWorking,
                isRunning = isRunning,
                isPaused = isPaused,
                isSessionFinished = isSessionFinished,
                onStartClick = { viewModel.startTimer() },
                onPauseClick = { viewModel.pauseTimer() },
                onResumeClick = { viewModel.resumeTimer() },
                onStopClick = { viewModel.stopTimer() },
               // onRestartClick = { viewModel.restartSession() }

            )

            LaunchedEffect(sessionEnded) {
                if (sessionEnded) {
                    val player = MediaPlayer.create(context, R.raw.finish)
                    player?.start()
                    player?.setOnCompletionListener { mp ->
                        mp.release()
                    }
                    viewModel.resetSessionFinished()
                }
            }
        }
    }
}