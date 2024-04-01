package com.example.blooddonationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.blooddonationapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_Up : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView12.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val email = binding.emailET.text.toString()
            val pass = binding.passET.text.toString()
            val confirmpass = binding.confrimpassET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()){
                if (pass == confirmpass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent = Intent(this, LoginPage::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Password not matching !!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Empty Fields not allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}