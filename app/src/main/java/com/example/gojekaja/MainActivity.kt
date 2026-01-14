package com.example.gojekaja

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gojekaja.ui.auth.MasukActivity
import com.example.gojekaja.utils.SessionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sessionManager = SessionManager(this)
        if (sessionManager.fetchAuthToken() == null) {
            startActivity(Intent(this, MasukActivity::class.java))
        } else {
            // Should go to HomeActivity if logged in
            startActivity(Intent(this, MasukActivity::class.java))
        }
        finish()
    }
}