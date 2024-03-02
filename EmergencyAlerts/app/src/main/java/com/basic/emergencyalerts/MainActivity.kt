package com.basic.emergencyalerts
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.basic.emergencyalerts.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("users")
        binding.regionSpinnerView.setItems(regions)

        binding.buttonSendAlert.setOnClickListener {
            val message = binding.editTextAlertMessage.text.toString().trim()
            val selectedRegionIndex = binding.regionSpinnerView.selectedIndex

            val selectedRegion = if (selectedRegionIndex != -1) {
                regions[selectedRegionIndex]
            } else {
                ""
            }

            if (message.isEmpty()) {
                showAlert("Please enter a message")
            } else {
                showAlert("Alert message: $message\nRegion: $selectedRegion")
            }
        }
    }

    private fun showAlert(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle("Alert")
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
