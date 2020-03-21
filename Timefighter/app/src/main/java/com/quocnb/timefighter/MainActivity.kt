package com.quocnb.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val INITIAL_TIME_LEFT = 5

class MainActivity : AppCompatActivity() {

    private lateinit var gameScoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton: Button
    private lateinit var countDownTimer: CountDownTimer

    private var score = 0
    private var gameStarted = false
    private var initialCountDown: Long = INITIAL_TIME_LEFT.toLong() * 1000
    private var countDownInterval: Long = 1000
    private var timeLeft = INITIAL_TIME_LEFT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // connect views to variables
        gameScoreTextView = findViewById(R.id.A01_game_score_tv)
        timeLeftTextView = findViewById(R.id.A01_time_left_tv)

        tapMeButton = findViewById(R.id.A01_tap_me_btn)
        tapMeButton.setOnClickListener {
            incrementScore()
        }
        resetGame()
    }

    private fun updateGameScoreText() {
        gameScoreTextView.text = getString(R.string.game_score_tv, score.toString())
    }

    private fun updateTimeLeftText() {
        timeLeftTextView.text = getString(R.string.time_left_tv, timeLeft.toString())
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }
        score += 1
        updateGameScoreText()
    }

    private fun resetGame() {
        score = 0
        updateGameScoreText()
        timeLeft = INITIAL_TIME_LEFT
        updateTimeLeftText()

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000
                updateTimeLeftText()
            }
            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score.toString()), Toast.LENGTH_LONG).show()
        resetGame()
    }
}
