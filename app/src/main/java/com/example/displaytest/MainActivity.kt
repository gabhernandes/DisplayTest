package com.example.displaytest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var startTestButton: Button
    private lateinit var passButton: Button
    private lateinit var testDisplayView: TestDisplayView
    private val timeoutHandler = Handler(Looper.getMainLooper())
    private val timeoutRunnable = Runnable {
        Toast.makeText(this@MainActivity, "Teste falhou", Toast.LENGTH_SHORT).show()
        resetToInitialScreen()
    }

    private val timeoutDuration = 10000L // 10 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startTestButton = findViewById(R.id.startTestButton)
        passButton = findViewById(R.id.passButton)
        testDisplayView = findViewById(R.id.testDisplayView)

        startTestButton.setOnClickListener {
            startTest()
        }

        passButton.setOnClickListener {
            Toast.makeText(this, "Teste passou", Toast.LENGTH_SHORT).show()
            resetToInitialScreen()
        }

        resetToInitialScreen()
    }

    private fun startTest() {
        startTestButton.visibility = Button.GONE
        testDisplayView.visibility = Button.VISIBLE
        passButton.visibility = Button.GONE

        testDisplayView.startTest()

        timeoutHandler.postDelayed(timeoutRunnable, timeoutDuration)
    }

    private fun resetToInitialScreen() {
        startTestButton.visibility = Button.VISIBLE
        testDisplayView.visibility = Button.GONE
        passButton.visibility = Button.GONE
        timeoutHandler.removeCallbacks(timeoutRunnable)
        testDisplayView.resetTest()
    }


    fun showPassButton() {
        passButton.visibility = Button.VISIBLE
        timeoutHandler.removeCallbacks(timeoutRunnable)
    }
}