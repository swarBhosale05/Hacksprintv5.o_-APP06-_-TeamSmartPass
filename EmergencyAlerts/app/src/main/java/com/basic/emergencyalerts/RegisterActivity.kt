package com.basic.emergencyalerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.basic.emergencyalerts.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhoneNumber.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                val result = dbHelper.insertUser(name, phone, password)
                if (result != -1L) {
                    showSnackbar("Registration successful")
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    showSnackbar("Registration failed")
                }
            } else {
                showSnackbar("Please fill all fields")
            }
        }
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}
