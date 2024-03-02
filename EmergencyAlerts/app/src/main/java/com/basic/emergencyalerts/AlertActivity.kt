package com.basic.emergencyalerts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.basic.emergencyalerts.databinding.ActivityAlertBinding

class AlertActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_SEND_SMS = 1
    private lateinit var binding: ActivityAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alertButton: Button = findViewById(R.id.alertButton)

        binding.textviewEmergency.setOnClickListener {
            startActivity(Intent(applicationContext,EmergencyContactsActivity::class.java))
        }
        alertButton.setOnClickListener {
            if (checkPermission()) {
                sendAlertSMS()
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
                    sendAlertSMS()
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

    private fun sendAlertSMS() {
        val smsManager = SmsManager.getDefault()
        val emergencyNumber = "+91 8087166115"
        val message = binding.editTextAlertMessage.text.toString()

        smsManager.sendTextMessage(emergencyNumber, null, message, null, null)

        Toast.makeText(
            this,
            "Alert message sent successfully",
            Toast.LENGTH_SHORT
        ).show()
    }
}
