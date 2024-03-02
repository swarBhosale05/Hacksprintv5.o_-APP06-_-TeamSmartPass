package com.basic.emergencyalerts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basic.emergencyalerts.databinding.ActivityLoginAdminBinding
import com.google.firebase.database.*

class LoginAdminActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityLoginAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("admin")

        binding.loginButton.setOnClickListener {
            val phoneNumber = binding.editTextPhoneNumber.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            startActivity(Intent(this,AlertActivity::class.java))
        }
    }

    private fun loginUser(phoneNumber: String, password: String) {
        database.ref.child(phoneNumber).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val adminData = snapshot.getValue(Admin::class.java)
                    if (adminData?.password == password) {
                        startActivity(Intent(applicationContext, AlertActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Incorrect password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
