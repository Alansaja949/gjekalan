package com.example.gojekaja.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.gojekaja.R
import com.example.gojekaja.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val ivLogo = findViewById<ImageView>(R.id.ivLogoSplash)
        val anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        anim.duration = 1500
        ivLogo.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            // CEK APAKAH USER SUDAH LOGIN ATAU BELUM
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                // Jika sudah login, langsung ke Home
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // Jika belum, ke GetStarted (Login/Daftar)
                startActivity(Intent(this, GetStartedActivity::class.java))
            }
            finish()
        }, 3000)
    }
}