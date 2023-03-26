package com.example.androidsemester4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.androidsemester4.Container
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.ActivityMainBinding
import com.example.androidsemester4.hideKeyboard
import com.example.androidsemester4.showSnackbar
import kotlinx.coroutines.launch

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
                SearchWeatherFragment()
            )
            .commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
