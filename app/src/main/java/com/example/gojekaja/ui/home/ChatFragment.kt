package com.example.gojekaja.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gojekaja.R

class ChatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        // Mengaktifkan klik untuk item chat 1
        view.findViewById<View>(R.id.itemChat1)?.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka chat Driver GoRide", Toast.LENGTH_SHORT).show()
        }

        // Mengaktifkan klik untuk item chat 2
        view.findViewById<View>(R.id.itemChat2)?.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka chat Martabak Pecenongan", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}