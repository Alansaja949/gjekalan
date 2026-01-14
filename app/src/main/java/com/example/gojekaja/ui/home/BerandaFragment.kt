package com.example.gojekaja.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gojekaja.R
import com.example.gojekaja.ui.food.FoodActivity
import com.example.gojekaja.ui.profile.ProfileActivity
import com.example.gojekaja.ui.ride.RideActivity

class BerandaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        // Klik Profil (Ikon di pojok kanan atas)
        view.findViewById<ImageView>(R.id.btnProfile)?.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        // Klik Kolom Pencarian
        view.findViewById<View>(R.id.btnSearch)?.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka pencarian...", Toast.LENGTH_SHORT).show()
        }

        // 1. Klik GoRide
        view.findViewById<View>(R.id.btnGoRide)?.setOnClickListener {
            startActivity(Intent(requireContext(), RideActivity::class.java))
        }

        // 2. Klik GoCar
        view.findViewById<View>(R.id.btnGoCar)?.setOnClickListener {
            startActivity(Intent(requireContext(), RideActivity::class.java)) // Sementara ke RideActivity
        }

        // 3. Klik GoFood
        view.findViewById<View>(R.id.btnGoFood)?.setOnClickListener {
            startActivity(Intent(requireContext(), FoodActivity::class.java))
        }

        // 4. Klik GoSend
        view.findViewById<View>(R.id.btnGoSend)?.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur GoSend segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // 5. Klik GoMart
        view.findViewById<View>(R.id.menuGoMart)?.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur GoMart segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // 6. Klik GoTagihan
        view.findViewById<View>(R.id.menuGoTagihan)?.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur GoTagihan segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // 7. Klik GoTransit
        view.findViewById<View>(R.id.menuGoTransit)?.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur GoTransit segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // 8. Klik Lainnya
        view.findViewById<View>(R.id.menuLainnya)?.setOnClickListener {
            Toast.makeText(requireContext(), "Menampilkan semua layanan...", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}