package com.example.chefdadakan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.chefdadakan.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Aktifkan tombol back di toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Resep"

        // Ambil data dari Intent yang dikirim HomeFragment
        val name = intent.getStringExtra("RECIPE_NAME") ?: "-"
        val category = intent.getStringExtra("RECIPE_CATEGORY") ?: "-"
        val image = intent.getStringExtra("RECIPE_IMAGE") ?: ""
        val instruction = intent.getStringExtra("RECIPE_INSTRUCTION") ?: "-"

        // Isi ke view
        findViewById<TextView>(R.id.tvDetailName).text = name
        findViewById<TextView>(R.id.tvDetailCategory).text = "Kategori: $category"
        findViewById<TextView>(R.id.tvDetailInstruction).text = instruction

        Glide.with(this)
            .load(image)
            .into(findViewById(R.id.imgDetail))
    }

    // Handle tombol back
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}