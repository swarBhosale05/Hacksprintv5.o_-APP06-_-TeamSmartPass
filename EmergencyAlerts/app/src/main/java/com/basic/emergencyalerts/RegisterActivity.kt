package com.basic.emergencyalerts
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basic.emergencyalerts.databinding.ActivityRegisterBinding
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val database = FirebaseDatabase.getInstance().getReference("users")
    private val regions = listOf(
        "Junnar taluka",
        "Ambegaon taluka",
        "Khed taluka",
        "Maval taluka",
        "Mulshi taluka",
        "Velhe taluka",
        "Bhor taluka",
        "Haveli taluka",
        "Purandar taluka",
        "Pune City taluka",
        "Indapur taluka",
        "Daund taluka",
        "Baramati taluka",
        "Shirur taluka"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerButton.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val phoneNumber = binding.editTextPhoneNumber.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (name.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else
                registerUser(name, phoneNumber, password)
        }
    }

    private fun registerUser(name: String, phoneNumber: String, password: String) {
        val userData = hashMapOf(
            "name" to name,
            "phoneNumber" to phoneNumber,
            "password" to password,
        )

        database.child("users").child(phoneNumber).setValue(userData)
            .addOnSuccessListener {
                startActivity(Intent(applicationContext, AlertActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
    }
}
