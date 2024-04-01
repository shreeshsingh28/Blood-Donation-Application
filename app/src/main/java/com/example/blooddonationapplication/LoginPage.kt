package com.example.blooddonationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.blooddonationapplication.databinding.ActivityLoginPageBinding
import com.example.blooddonationapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView9.setOnClickListener{
            val intent = Intent(this, Sign_Up::class.java)
            startActivity(intent)
        }

        binding.btnLogin1.setOnClickListener {
            val email = binding.emailET1.text.toString()
            val pass = binding.passET1.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("user", email)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Empty Fields not allowed !!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}