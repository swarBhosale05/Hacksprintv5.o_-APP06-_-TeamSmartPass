package com.basic.emergencyalerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.basic.emergencyalerts.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().getReference("users")
        binding.registerTextView.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.loginTextView.setOnClickListener {
            startActivity(Intent(this,LoginAdminActivity::class.java))
        }
        binding.loginButton.setOnClickListener {
            val phoneNumber = binding.editTextPhoneNumber.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginUser(phoneNumber, password)
        }
    }

    private fun loginUser(phoneNumber: String, password: String) {
        database.child("users").child(phoneNumber).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userData = snapshot.getValue(Users::class.java)
                    if (userData?.password == password) {
                        showSnackbar("Login successful")
                        startActivity(Intent(applicationContext, AlertActivity::class.java))
                    } else {
                        showSnackbar("Incorrect password")
                    }
                } else {
                    showSnackbar("User not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showSnackbar("Login failed")
            }
        })
    }
    private fun showSnackbar(message: String) {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}