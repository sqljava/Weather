package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.weather.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main, MainFragment())
            .commit()
    }
}