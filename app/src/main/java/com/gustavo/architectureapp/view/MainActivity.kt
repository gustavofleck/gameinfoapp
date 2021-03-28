package com.gustavo.architectureapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gustavo.architectureapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        supportFragmentManager.findFragmentById(R.id.navigation_fragment)

    }

}