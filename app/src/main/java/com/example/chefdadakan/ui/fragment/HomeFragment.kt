package com.example.chefdadakan.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chefdadakan.R
import com.example.chefdadakan.adapter.RecipeAdapter
import com.example.chefdadakan.model.Recipe
import com.example.chefdadakan.ui.DetailActivity
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private lateinit var rvRecipes: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var adapter: RecipeAdapter

    // Data dummy sementara sebelum Anggota 2 selesai setup API
    private val dummyRecipes = listOf(
        Recipe("1", "Ayam Goreng Crispy", "Chicken",
            "https://www.themealdb.com/images/media/meals/tyywsw1505930373.jpg",
            "Goreng ayam hingga crispy keemasan..."),
        Recipe("2", "Soto Ayam", "Chicken",
            "https://www.themealdb.com/images/media/meals/tvttqv1511181488.jpg",
            "Rebus ayam bersama rempah-rempah..."),
        Recipe("3", "Calamari Goreng", "Seafood",
            "https://www.themealdb.com/images/media/meals/xxrxux1503783827.jpg",
            "Goreng cumi dengan tepung renyah..."),
        Recipe("4", "Udang Saus Padang", "Seafood",
            "https://www.themealdb.com/images/media/meals/wrssvt1511556563.jpg",
            "Tumis udang dengan saus padang pedas..."),
        Recipe("5", "Coklat Lava Cake", "Dessert",
            "https://www.themealdb.com/images/media/meals/tbttqo1515997275.jpg",
            "Panggang cake coklat hingga lembut..."),
        Recipe("6", "Es Krim Vanilla", "Dessert",
            "https://www.themealdb.com/images/media/meals/uvsyxu1487327884.jpg",
            "Campur bahan dan bekukan semalam...")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi semua view
        rvRecipes = view.findViewById(R.id.rvRecipes)
        tabLayout = view.findViewById(R.id.tabLayout)
        progressBar = view.findViewById(R.id.progressBar)
        tvEmpty = view.findViewById(R.id.tvEmpty)

        setupRecyclerView()
        setupTabLayout()

        // Tampilkan semua resep di awal
        showRecipes(dummyRecipes)
    }

    private fun setupRecyclerView() {
        adapter = RecipeAdapter(emptyList()) { recipe ->
            // Saat card diklik, pindah ke DetailActivity dan kirim data
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.idMeal)
            intent.putExtra("RECIPE_NAME", recipe.strMeal)
            intent.putExtra("RECIPE_CATEGORY", recipe.strCategory)
            intent.putExtra("RECIPE_IMAGE", recipe.strMealThumb)
            intent.putExtra("RECIPE_INSTRUCTION", recipe.strInstructions)
            startActivity(intent)
        }

        rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        rvRecipes.adapter = adapter
    }

    private fun setupTabLayout() {
        // Tambah tab kategori
        tabLayout.addTab(tabLayout.newTab().setText("Semua"))
        tabLayout.addTab(tabLayout.newTab().setText("Chicken"))
        tabLayout.addTab(tabLayout.newTab().setText("Seafood"))
        tabLayout.addTab(tabLayout.newTab().setText("Dessert"))

        // Listener saat tab diklik
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showRecipes(dummyRecipes)
                    1 -> showRecipes(dummyRecipes.filter { it.strCategory == "Chicken" })
                    2 -> showRecipes(dummyRecipes.filter { it.strCategory == "Seafood" })
                    3 -> showRecipes(dummyRecipes.filter { it.strCategory == "Dessert" })
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun showRecipes(list: List<Recipe>) {
        if (list.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            rvRecipes.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            rvRecipes.visibility = View.VISIBLE
            adapter.updateData(list)
        }
    }
}