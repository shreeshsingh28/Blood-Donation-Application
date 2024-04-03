package com.example.blooddonationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Donate : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

       val out = findViewById<TextView>(R.id.editTextTextMultiLine)
        val email = intent.getStringExtra("user")
        val usernameTextView = findViewById<TextView>(R.id.userTV5)
        usernameTextView.text = email.toString()

        var bg2 = findViewById<TextView>(R.id.bg2)
        var docId = email.toString()
        db.collection("profile").document(docId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Retrieve data from the document
                    val bloodGroup = documentSnapshot.getString("bloodGroup")

                    // Display the retrieved data in TextViews
                    bg2.setText("$bloodGroup")
                } else {
                    showToast("No profile found for username: $docId")
                }
            }
            .addOnFailureListener { e -> showToast("Error: " + e.message) }

        var backbtn = findViewById<Button>(R.id.backbtn5)
        backbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val email = findViewById<TextView>(R.id.userTV5)
            intent.putExtra("user", email.text)
            startActivity(intent)
        }

        var searchbtn = findViewById<Button>(R.id.searchbtn)
        searchbtn.setOnClickListener {

            val bloodGroupValue = bg2.text.toString()
            val excludedUsername = email.toString()

            db.collection("request")
                .whereEqualTo("bloodGroup", bloodGroupValue)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val userData = StringBuilder()

                    for (document in querySnapshot.documents) {
                        val username = document.getString("username")
                        if (username != excludedUsername) {
                            val mobileNo = document.getString("mobileNo")
                            val city = document.getString("city")
                            val state = document.getString("state")

                            userData.append("e-mail: $username\n")
                            userData.append("Mobile No: $mobileNo\n")
                            userData.append("Location: $city, $state\n\n")
                        }
                    }

                    if (userData.isNotEmpty()) {
                        // Set the concatenated data to the TextView
                        out.text = userData.toString()
                    } else {
                        showToast("No profiles found for blood group: $bloodGroupValue")
                    }
                }
                .addOnFailureListener { e ->
                    showToast("Error: ${e.message}")
                }
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

