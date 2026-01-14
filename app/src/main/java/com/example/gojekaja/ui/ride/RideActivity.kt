package com.example.gojekaja.ui.ride

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gojekaja.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride)

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        
        // Perbaikan: Gunakan View (umum) agar tidak error saat cast dari XML
        val btnBack = findViewById<View>(R.id.btnBack)
        val etPickup = findViewById<EditText>(R.id.etPickup)
        val etDestination = findViewById<EditText>(R.id.etDestination)
        val btnOrder = findViewById<Button>(R.id.btnOrder)

        btnBack.setOnClickListener { 
            finish() 
        }

        btnOrder.setOnClickListener {
            val pickup = etPickup.text.toString().trim()
            val destination = etDestination.text.toString().trim()
            val userId = auth.currentUser?.uid

            if (pickup.isEmpty()) {
                Toast.makeText(this, "Lokasi jemput jangan kosong ya!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (destination.isEmpty()) {
                Toast.makeText(this, "Tujuan kamu ke mana nih?", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userId == null) {
                Toast.makeText(this, "Wah, kamu harus login dulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Efek Loading (Opsional: Matikan tombol agar tidak double klik)
            btnOrder.isEnabled = false
            btnOrder.text = "Memproses..."

            val orderData = hashMapOf(
                "userId" to userId,
                "serviceType" to "GoRide",
                "origin" to pickup,
                "destination" to destination,
                "status" to "Selesai",
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("orders")
                .add(orderData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Yey! Pesanan kamu berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    btnOrder.isEnabled = true
                    btnOrder.text = "Pesan GoRide"
                    Toast.makeText(this, "Gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}