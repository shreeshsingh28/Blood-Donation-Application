package com.example.blooddonationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class History : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val email = intent.getStringExtra("user")
        val usernameTextView = findViewById<TextView>(R.id.userTV3)
        usernameTextView.text = email.toString()

        var fr = findViewById<TextView>(R.id.fr)
        var mon = findViewById<TextView>(R.id.mon)
        var re = findViewById<TextView>(R.id.re)
        var ti = findViewById<TextView>(R.id.ti)

        var docId = email.toString()
        val fetchBtn2 = findViewById<Button>(R.id.fetchbtn2)
        fetchBtn2.setOnClickListener {
            db.collection("history").document(docId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Retrieve data from the document
                        val frequency = documentSnapshot.getLong("frequency")
                        val monetary = documentSnapshot.getLong("monetary")
                        val recency = documentSnapshot.getLong("recency")
                        val time = documentSnapshot.getLong("time")


                        // Display the retrieved data in TextViews
                        fr.setText("$frequency")
                        mon.setText("$monetary")
                        re.setText("$recency")
                        ti.setText("$time")
                    } else {
                        showToast("No profile found for username: $docId")
                    }
                }
                .addOnFailureListener { e -> showToast("Error: " + e.message) }
        }
        var backbtn3 = findViewById<Button>(R.id.backbtn3)
        backbtn3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val email = findViewById<TextView>(R.id.userTV2)
            intent.putExtra("user", email.text)
            startActivity(intent)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}