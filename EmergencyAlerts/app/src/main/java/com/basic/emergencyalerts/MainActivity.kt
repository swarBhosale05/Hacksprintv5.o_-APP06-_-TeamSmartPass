package com.basic.emergencyalerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basic.emergencyalerts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: AlertAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        adapter = AlertAdapter()
        binding.alertsRecyclerView.adapter = adapter
        binding.alertsRecyclerView.layoutManager = LinearLayoutManager(this)

        val offlineAlerts = listOf("Earthquake alert", "Flood warning", "Tornado watch")
        adapter.submitList(offlineAlerts)

        // Show or hide RecyclerView based on data availability
        if (offlineAlerts.isEmpty()) {
            binding.alertsRecyclerView.visibility = View.GONE
            binding.noDataTextView.visibility = View.VISIBLE
        } else {
            binding.alertsRecyclerView.visibility = View.VISIBLE
            binding.noDataTextView.visibility = View.GONE
        }
    }
}