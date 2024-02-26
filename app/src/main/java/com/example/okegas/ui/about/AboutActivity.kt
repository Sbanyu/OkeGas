package com.example.okegas.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.okegas.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_title)
    }
}