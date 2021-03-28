package com.gustavo.architectureapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gustavo.architectureapp.R

class GameDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        supportActionBar?.hide()

    }
}