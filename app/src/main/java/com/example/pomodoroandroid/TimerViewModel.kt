package com.example.pomodoroandroid.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private val workDuration = 25 * 60 * 1000L
    private val breakDuration = 5 * 60 * 1000L

    private val _timeLeft = MutableStateFlow(formatTime(workDuration))
    val timeLeft: StateFlow<String> = _timeLeft

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _isWorking = MutableStateFlow(true)
    val isWorking: StateFlow<Boolean> = _isWorking

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused

    private val _isSessionFinished = MutableStateFlow(false)
    val isSessionFinished: StateFlow<Boolean> = _isSessionFinished

    private var timer: CountDownTimer? = null
    private var totalTime: Long = workDuration
    private var remainingTime: Long = totalTime

    fun startTimer() {
        _isRunning.value = true
        _isPaused.value = false
        launchTimer()

    }

    fun pauseTimer() {
        timer?.cancel()
        _isRunning.value = false
        _isPaused.value = true
    }

    fun resumeTimer() {
        _isRunning.value = true
        _isPaused.value = false
        startTimer() // käyttää remainingTime
    }

    fun stopTimer() {
        timer?.cancel()
        _isRunning.value = false
        _isPaused.value = false
        _isSessionFinished.value = false

        _isWorking.value = true
        totalTime = 25 * 60 * 1000L
        remainingTime = totalTime
        _timeLeft.value = formatTime(totalTime)
    }

   /* fun resetSession() {
        stopTimer()
        _isWorking.value = true
        totalTime = 25 * 60 * 1000L
        remainingTime = totalTime
        _isSessionFinished.value = false
    }  */

    fun resetSessionFinished() {
        _isSessionFinished.value = false
    }

    private fun launchTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                _timeLeft.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                _isRunning.value = false
                _isPaused.value = false
                _isSessionFinished.value = true

                _isWorking.value = !_isWorking.value
                totalTime = if (_isWorking.value) workDuration else breakDuration
                remainingTime = totalTime
                _timeLeft.value = formatTime(totalTime)
            }
        }.start()
    }

    private fun formatTime(millis: Long): String {
        val minutes = (millis / 1000) / 60
        val seconds = (millis / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}


