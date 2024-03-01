package com.basic.emergencyalerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.basic.emergencyalerts.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DatabaseHelper(this)


        dbHelper = DatabaseHelper(this)

        binding.loginButton.setOnClickListener {
            val phone = binding.editTextPhoneNumber.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (phone.isNotEmpty() && password.isNotEmpty()) {
                val loggedIn = dbHelper.loginUser(phone, password)
                if (loggedIn) {
                    showSnackbar("Login successful")
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    showSnackbar("Invalid phone number or password")
                }
            } else {
                showSnackbar("Please enter phone number and password")
            }
        }

        binding.registerTextView.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}