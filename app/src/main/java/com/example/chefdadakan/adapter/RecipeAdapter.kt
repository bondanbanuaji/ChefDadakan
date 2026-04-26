package com.example.chefdadakan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chefdadakan.R
import com.example.chefdadakan.model.Recipe

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit  // callback saat card diklik
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    // ViewHolder: memegang referensi semua view dalam satu item card
    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgRecipe: ImageView = itemView.findViewById(R.id.imgRecipe)
        val tvRecipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvSeeDetail: TextView = itemView.findViewById(R.id.tvSeeDetail)
    }

    // Dipanggil saat RecyclerView butuh view baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    // Dipanggil untuk mengisi data ke view sesuai posisi
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.tvRecipeName.text = recipe.strMeal
        holder.tvCategory.text = recipe.strCategory

        // Load gambar dari URL menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(recipe.strMealThumb)
            .placeholder(R.drawable.ic_home) // gambar sementara saat loading
            .into(holder.imgRecipe)

        // Klik seluruh card
        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }

        // Klik tombol "Lihat Resep"
        holder.tvSeeDetail.setOnClickListener {
            onItemClick(recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size

    // Fungsi update data dari luar (dipanggil saat tab ganti atau data API masuk)
    fun updateData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}