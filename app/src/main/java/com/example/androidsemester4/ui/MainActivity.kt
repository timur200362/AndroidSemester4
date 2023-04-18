package com.example.androidsemester4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.ActivityMainBinding
import java.io.Closeable

class MainActivity : AppCompatActivity(){
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                SearchWeatherFragment()
            )
            .commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
