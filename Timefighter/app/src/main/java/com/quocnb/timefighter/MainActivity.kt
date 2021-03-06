package com.quocnb.timefighter

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val INITIAL_TIME_LEFT = 30
private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private lateinit var gameScoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton: Button
    private lateinit var countDownTimer: CountDownTimer

    private var score = 0
    private var gameStarted = false
    private var countDownInterval: Long = 1000
    private var timeLeft = INITIAL_TIME_LEFT

    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // connect views to variables
        gameScoreTextView = findViewById(R.id.A01_game_score_tv)
        timeLeftTextView = findViewById(R.id.A01_time_left_tv)
        tapMeButton = findViewById(R.id.A01_tap_me_btn)
        tapMeButton.setOnClickListener {
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            it.startAnimation(bounceAnimation)
            incrementScore()
        }
        setupGame(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()
        Log.d(TAG, "Saving score: $score and time left: $timeLeft")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.A00_action_settings) {
            showInfo()
        }
        return true
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.A00_about_dialog_title, BuildConfig.VERSION_NAME)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(R.string.A00_about_dialog_message)
        builder.create().show()
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
        setupGame(null)
    }

    private fun recoverGame(savedInstanceState: Bundle?) {
        setupGame(savedInstanceState)
    }

    private fun setupGame(savedInstanceState: Bundle?) {
        score = 0
        timeLeft = INITIAL_TIME_LEFT
        savedInstanceState?.let {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
        }
        Log.d(TAG, "time left: $timeLeft")
        updateGameScoreText()
        updateTimeLeftText()

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000
                updateTimeLeftText()
            }
            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = if (savedInstanceState != null) {
            countDownTimer.start()
            true
        } else {
            false
        }
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
