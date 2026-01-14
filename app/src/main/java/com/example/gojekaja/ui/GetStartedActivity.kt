package com.example.gojekaja.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.gojekaja.R
import com.example.gojekaja.ui.auth.MasukActivity
import com.example.gojekaja.ui.auth.DaftarduluActivity

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        findViewById<Button>(R.id.btnMasuk).setOnClickListener {
            startActivity(Intent(this, MasukActivity::class.java))
        }

        findViewById<Button>(R.id.btnDaftar).setOnClickListener {
            startActivity(Intent(this, DaftarduluActivity::class.java))
        }
    }
}