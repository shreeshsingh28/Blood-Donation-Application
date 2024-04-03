package com.example.blooddonationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = intent.getStringExtra("user")
        val username = findViewById<TextView>(R.id.userTV1)
        username.text = email.toString()

        var logoutbtn = findViewById<Button>(R.id.logoutbtn)
        logoutbtn.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        var profile = findViewById<Button>(R.id.profilebtn)
        profile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            intent.putExtra("user", username.text.toString())
            startActivity(intent)
        }

        var history = findViewById<Button>(R.id.historybtn)
        history.setOnClickListener {
            val intent = Intent(this, History::class.java)
            intent.putExtra("user", username.text.toString())
            startActivity(intent)
        }

        var req = findViewById<Button>(R.id.requestbtn)
        req.setOnClickListener {
            val intent = Intent(this, Request::class.java)
            intent.putExtra("user", username.text.toString())
            startActivity(intent)
        }


    }
}