package com.example.assignmentone

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var score = 0
    private lateinit var scoreTextView: TextView
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize score and view
        scoreTextView = findViewById(R.id.scoreTextView)
        savedInstanceState?.let {
            score = it.getInt("SCORE", 0)
            updateScoreDisplay()
        }

        // Initialize buttons
        findViewById<Button>(R.id.button1).setOnClickListener { onButtonClick() }
        findViewById<Button>(R.id.button2).setOnClickListener { onButtonClick() }
        findViewById<Button>(R.id.button3).setOnClickListener { onButtonClick() }

        // Initialize sound
        mediaPlayer = MediaPlayer.create(this, R.raw.click_sound)
    }
    private fun onWin() {
        // Play the winning sound
        mediaPlayer = MediaPlayer.create(this, R.raw.win_sound)
        mediaPlayer?.start()

        // Log the win for debugging
        Log.d("MainActivity", "You win! Score reached 15.")

        // Show a message to the user
        scoreTextView.text = getString(R.string.win_message)

        // Disable all buttons
        findViewById<Button>(R.id.button1).isEnabled = false
        findViewById<Button>(R.id.button2).isEnabled = false
        findViewById<Button>(R.id.button3).isEnabled = false
    }

    private fun onButtonClick() {
        if (score < 15) {
            score++
            updateScoreDisplay()
            playSound()

            // Check for winning condition
            if (score == 15) {
                onWin()
            }
        }
    }

    private fun updateScoreDisplay() {
        scoreTextView.text = getString(R.string.score_label, score)
        Log.d("MainActivity", "Score updated: $score")
    }

    private fun playSound() {
        mediaPlayer?.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SCORE", score)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}
