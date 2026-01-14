package com.example.gojekaja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gojekaja.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // 1. Validasi Input Kosong
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tolong isi semua data ya!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Validasi Password Cocok
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwordnya gak sama nih!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Validasi Panjang Password
            if (password.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter ya!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 4. Proses Daftar ke Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Yey! Akun kamu berhasil dibuat", Toast.LENGTH_SHORT).show()
                        
                        // Langsung masuk ke Beranda
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finishAffinity() 
                    } else {
                        // Jika gagal (misal email sudah terpakai)
                        Toast.makeText(this, "Gagal daftar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // Tombol "Sudah punya akun? Masuk aja"
        tvLogin.setOnClickListener {
            finish()
        }
    }
}