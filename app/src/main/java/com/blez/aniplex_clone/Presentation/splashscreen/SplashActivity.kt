package com.blez.aniplex_clone.Presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.blez.aniplex_clone.Presentation.MainActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        supportActionBar?.hide()
        runActivity()


    }

    fun runActivity() {
        if (!isDestroyed) {
            val intent = Intent(this, MainActivity::class.java)
            val mtask = timerTask { startActivity(intent)
            finish()}
            val timer = Timer()

            timer.schedule(mtask,2000)
        }

    }
}