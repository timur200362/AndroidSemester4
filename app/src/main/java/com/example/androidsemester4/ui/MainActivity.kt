package com.example.androidsemester4.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.ActivityMainBinding
import com.example.androidsemester4.ui.mvi.SearchWeatherFragmentMVI

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                SearchWeatherFragmentMVI()
            )
            .commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
