package com.example.blooddonationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val email = intent.getStringExtra("user")
        val username = findViewById<TextView>(R.id.userTV2)
        username.text = email.toString()
    }
}