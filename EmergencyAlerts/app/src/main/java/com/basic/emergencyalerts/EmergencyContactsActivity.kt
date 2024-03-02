package com.basic.emergencyalerts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmergencyContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)

        val tvEmergencyContacts: TextView = findViewById(R.id.tvEmergencyContacts)

        tvEmergencyContacts.setOnClickListener {
            val phoneNumber = extractPhoneNumber(tvEmergencyContacts.text.toString())
            initiatePhoneCall(phoneNumber)
        }
    }

    private fun extractPhoneNumber(text: String): String {
        return text.replace("[^0-9]".toRegex(), "")
    }

    private fun initiatePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
}