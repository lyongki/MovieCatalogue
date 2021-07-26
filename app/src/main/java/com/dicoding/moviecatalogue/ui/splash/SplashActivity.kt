package com.dicoding.moviecatalogue.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.ui.film.FilmActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                Intent(
                    this@SplashActivity,
                    FilmActivity::class.java
                ).apply {
                    startActivity(this)
                    finish()
                }
            }
        }, 3000)
    }
}