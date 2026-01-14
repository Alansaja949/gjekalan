package com.example.gojekaja.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.gojekaja.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AktivitasFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var containerLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_aktivitas, container, false)
        
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        containerLayout = view.findViewById(R.id.containerAktivitas)

        loadOrders()

        return view
    }

    private fun loadOrders() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("orders")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Toast.makeText(context, "Gagal memuat data: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                containerLayout.removeAllViews()

                if (snapshots != null && !snapshots.isEmpty) {
                    for (doc in snapshots) {
                        val docId = doc.id
                        val service = doc.getString("serviceType") ?: "Order"
                        val origin = doc.getString("origin") ?: "-"
                        val destination = doc.getString("destination") ?: "-"
                        val status = doc.getString("status") ?: "Selesai"
                        val timestamp = doc.getLong("timestamp") ?: 0L

                        val itemView = layoutInflater.inflate(R.layout.item_aktivitas, containerLayout, false)
                        
                        // Set Data ke View
                        itemView.findViewById<TextView>(R.id.tvService).text = service
                        itemView.findViewById<TextView>(R.id.tvOrigin).text = origin
                        itemView.findViewById<TextView>(R.id.tvDestination).text = destination
                        itemView.findViewById<TextView>(R.id.tvStatus).text = status
                        
                        // Format Tanggal
                        if (timestamp > 0) {
                            val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
                            itemView.findViewById<TextView>(R.id.tvDate).text = sdf.format(Date(timestamp))
                        }

                        // Logika Hapus
                        itemView.findViewById<ImageView>(R.id.btnDelete).setOnClickListener {
                            showDeleteDialog(docId)
                        }

                        containerLayout.addView(itemView)
                    }
                }
            }
    }

    private fun showDeleteDialog(docId: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Riwayat")
            .setMessage("Apakah Anda yakin ingin menghapus riwayat pesanan ini?")
            .setPositiveButton("Hapus") { _, _ ->
                deleteOrder(docId)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteOrder(docId: String) {
        db.collection("orders").document(docId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Riwayat dihapus", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Gagal menghapus: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}