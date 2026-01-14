package com.example.gojekaja.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.gojekaja.R
import com.example.gojekaja.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MasukActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // 1. Konfigurasi Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Pastikan ini ID WEB, bukan Android
            .requestEmail()
            .build()
        
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val btnLoginGoogle = findViewById<Button>(R.id.btnLoginGoogle)
        
        // 2. Launcher untuk menangkap hasil login
        val googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                val errorMsg = when (e.statusCode) {
                    10 -> "Error 10: SHA-1 belum terdaftar di Firebase atau Web Client ID salah."
                    12500 -> "Error 12500: Masalah konfigurasi Google Play Services."
                    else -> "Gagal: ${e.message} (Status: ${e.statusCode})"
                }
                Log.e("GoogleAuth", errorMsg)
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            }
        }

        btnLoginGoogle.setOnClickListener {
            googleSignInClient.signOut() // Paksa user pilih akun lagi
            googleLauncher.launch(googleSignInClient.signInIntent)
        }
        
        // Setup tombol lainnya...
        setupOtherButtons()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Berhasil masuk!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Firebase Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupOtherButtons() {
        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Gagal: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, DaftarduluActivity::class.java))
        }
    }
}