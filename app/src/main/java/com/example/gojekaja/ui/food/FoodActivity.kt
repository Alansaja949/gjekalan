package com.example.gojekaja.ui.food

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gojekaja.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FoodActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // 1. Tombol Kembali
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // 2. Set Klik pada Item Restoran (Contoh Simulasi Pesan)
        val res1 = findViewById<LinearLayout>(R.id.resItem1)
        val res2 = findViewById<LinearLayout>(R.id.resItem2)
        val res3 = findViewById<LinearLayout>(R.id.resItem3)

        res1.setOnClickListener { orderFood("Martabak Pecenongan 78") }
        res2.setOnClickListener { orderFood("Ayam Bakar Wong Solo") }
        res3.setOnClickListener { orderFood("Kopi Kenangan") }
    }

    private fun orderFood(restaurantName: String) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "Silakan login dulu ya!", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulasi Pesan Makanan Langsung (Untuk fungsionalitas riwayat)
        val orderData = hashMapOf(
            "userId" to userId,
            "serviceType" to "GoFood",
            "origin" to restaurantName,
            "destination" to "Rumah Kamu",
            "status" to "Selesai",
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("orders")
            .add(orderData)
            .addOnSuccessListener {
                Toast.makeText(this, "Pesanan dari $restaurantName Berhasil!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal memesan: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}