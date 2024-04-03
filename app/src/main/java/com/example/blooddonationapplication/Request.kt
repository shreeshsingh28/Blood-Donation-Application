package com.example.blooddonationapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore

class Request : AppCompatActivity() {

    data class User(
        var username: String,
        var bloodGroup: String,
        var mobileNo: String,
        var city: String,
        var state: String
    )

    private val db = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        val email = intent.getStringExtra("user")
        val username = findViewById<TextView>(R.id.userTV4)
        username.text = email.toString()

        var mob1 = findViewById<TextView>(R.id.mob1)
        var bg1 = findViewById<TextView>(R.id.bg1)
        var city1 = findViewById<TextView>(R.id.city1)
        var state1 = findViewById<TextView>(R.id.state1)

        var docId = email.toString()
        val fetchBtn = findViewById<Button>(R.id.fetchbtn3)
        fetchBtn.setOnClickListener {
            db.collection("profile").document(docId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Retrieve data from the document
                        val bloodGroup = documentSnapshot.getString("bloodGroup")
                        val mobileNo = documentSnapshot.getString("mobileNo")
                        val city2 = documentSnapshot.getString("city")
                        val state2 = documentSnapshot.getString("state")

                        // Display the retrieved data in TextViews
                        bg1.setText("$bloodGroup")
                        mob1.setText("$mobileNo")
                        city1.setText("$city2")
                        state1.setText("$state2")
                    } else {
                        showToast("No profile found for username: $docId")
                    }
                }
                .addOnFailureListener { e -> showToast("Error: " + e.message) }
        }

        var backbtn = findViewById<Button>(R.id.backbtn4)
        backbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val email = findViewById<TextView>(R.id.userTV4)
            intent.putExtra("user", email.text)
            startActivity(intent)
        }

        val reqbtn = findViewById<Button>(R.id.reqbtn)
        reqbtn.setOnClickListener {
            val user = Request.User(
                username = email.toString(),
                bloodGroup = bg1.text.toString(),
                mobileNo = mob1.text.toString(),
                city = city1.text.toString(),
                state = state1.text.toString()
            )
            addUserToFirestore(user)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addUserToFirestore(user: Request.User) {
        db.collection("request").document(user.username)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "DocumentSnapshot added for user: ${user.username}", Toast.LENGTH_SHORT).show()
                // Optionally, you can show a toast or update UI to inform the user
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding document: $e", Toast.LENGTH_SHORT).show()
                // Optionally, you can show a toast or update UI to inform the user
            }
    }
}