package com.basic.emergencyalerts

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlertActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_SEND_SMS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        val alertButton: Button = findViewById(R.id.alertButton)

        alertButton.setOnClickListener {
            if (checkPermission()) {
                sendAdminAlert("Emergency Alert: Earthquake detected in your area. Take shelter immediately.")
            } else {
                requestPermission()
            }
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS),
            PERMISSION_REQUEST_SEND_SMS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_SEND_SMS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendAdminAlert("Emergency Alert: Earthquake detected in your area. Take shelter immediately.")
                } else {
                    Toast.makeText(
                        this,
                        "Permission denied to send SMS",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // Function to send admin alerts to registered users
    private fun sendAdminAlert(message: String) {
        val registeredUserPhoneNumbers = getRegisteredUserPhoneNumbers()

        for (phoneNumber in registeredUserPhoneNumbers) {
            sendAlertSMS(message, phoneNumber)
        }
    }

    // Function to retrieve phone numbers of registered users
    private fun getRegisteredUserPhoneNumbers(): List<String> {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userPhoneNumbersJson = sharedPreferences.getString("registered_user_phone_numbers", null)

        return if (userPhoneNumbersJson != null) {
            Gson().fromJson(userPhoneNumbersJson, object : TypeToken<List<String>>() {}.type)
        } else {
            emptyList()
        }
    }

    // Function to send SMS alerts
    private fun sendAlertSMS(message: String, phoneNumber: String) {
        val smsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(phoneNumber, null, message, null, null)

        Toast.makeText(
            this,
            "Alert message sent successfully to $phoneNumber",
            Toast.LENGTH_SHORT
        ).show()
    }
}
