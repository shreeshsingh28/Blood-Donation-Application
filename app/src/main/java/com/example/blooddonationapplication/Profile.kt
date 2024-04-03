package com.example.blooddonationapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class Profile : AppCompatActivity() {
    data class User(
        var username: String,
        var firstName: String,
        var lastName: String,
        var sex: String,
        var bloodGroup: String,
        var mobileNo: String,
        var city: String,
        var state: String
    )
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val email = intent.getStringExtra("user")
        val usernameTextView = findViewById<TextView>(R.id.userTV2)
        usernameTextView.text = email.toString()
        var fn = findViewById<TextView>(R.id.fn)
        var ln = findViewById<TextView>(R.id.ln)
        var sex = findViewById<TextView>(R.id.sex)
        var bg = findViewById<TextView>(R.id.bg)
        var mob = findViewById<TextView>(R.id.mob)
        var city = findViewById<TextView>(R.id.city)
        var state = findViewById<TextView>(R.id.state)

        val updatebtn = findViewById<Button>(R.id.updatebtn)
        updatebtn.setOnClickListener {
            val username = email.toString()
            val firstName = fn.text.toString()
            val lastName = ln.text.toString()
            val sex = sex.text.toString()
            val bloodGroup = bg.text.toString()
            val mobileNo = mob.text.toString()
            val city = city.text.toString()
            val state = state.text.toString()

            if (validateInputs(username, firstName, lastName, sex, bloodGroup, mobileNo)) {
                val user = User(
                    username = username,
                    firstName = firstName,
                    lastName = lastName,
                    sex = sex,
                    bloodGroup = bloodGroup,
                    mobileNo = mobileNo,
                    city = city,
                    state = state
                )
                addUserToFirestore(user)
            }
        }


        var docId = email.toString()
        val fetchBtn = findViewById<Button>(R.id.fetch)
        fetchBtn.setOnClickListener {
            db.collection("profile").document(docId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Retrieve data from the document
                        val firstName = documentSnapshot.getString("firstName")
                        val lastName = documentSnapshot.getString("lastName")
                        val sex1 = documentSnapshot.getString("sex")
                        val bloodGroup = documentSnapshot.getString("bloodGroup")
                        val mobileNo = documentSnapshot.getString("mobileNo")
                        val city1 = documentSnapshot.getString("city")
                        val state1 = documentSnapshot.getString("state")

                        // Display the retrieved data in TextViews
                        fn.setText("$firstName")
                        ln.setText("$lastName")
                        sex.setText("$sex1")
                        bg.setText("$bloodGroup")
                        mob.setText("$mobileNo")
                        city.setText("$city1")
                        state.setText("$state1")
                    } else {
                        showToast("No profile found for username: $docId")
                    }
                }
                .addOnFailureListener { e -> showToast("Error: " + e.message) }
        }

        var backbtn = findViewById<Button>(R.id.backbtn)
        backbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val email = findViewById<TextView>(R.id.userTV2)
            intent.putExtra("user", email.text)
            startActivity(intent)
        }



    }

    private fun validateInputs(username: String, firstName: String, lastName: String, sex: String, bloodGroup: String, mobileNo: String): Boolean {
        // Validate sex
        if (!isValidSex(sex)) {
            showToast("Invalid sex. Please enter male, female, or others.")
            return false
        }

        // Validate blood group
        if (!isValidBloodGroup(bloodGroup)) {
            showToast("Invalid blood group. Please enter a valid blood group.")
            return false
        }

        // Validate mobile number length
        if (mobileNo.length != 10) {
            showToast("Mobile number should be 10 digits long.")
            return false
        }

        // All validations passed
        return true
    }

    private fun isValidSex(sex: String): Boolean {
        return sex.equals("male", ignoreCase = true) || sex.equals("female", ignoreCase = true) || sex.equals("others", ignoreCase = true)
    }

    private fun isValidBloodGroup(bloodGroup: String): Boolean {
        val validBloodGroups = arrayOf("O+", "A+", "B+", "AB+", "O-", "A-", "B-", "AB-")
        return validBloodGroups.contains(bloodGroup.toUpperCase())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addUserToFirestore(user: User) {
        db.collection("profile").document(user.username)
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
