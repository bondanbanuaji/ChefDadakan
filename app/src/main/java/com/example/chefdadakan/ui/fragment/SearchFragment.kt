package com.example.chefdadakan.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chefdadakan.R
import com.example.chefdadakan.adapter.RecipeAdapter
import com.example.chefdadakan.model.Recipe
import com.example.chefdadakan.ui.DetailActivity

class SearchFragment : Fragment() {

    // Deklarasi semua view
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var progressBarSearch: ProgressBar
    private lateinit var rvSearchResult: RecyclerView
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var layoutInitial: LinearLayout
    private lateinit var tvResultInfo: TextView
    private lateinit var tvEmptyTitle: TextView
    private lateinit var tvEmptySubtitle: TextView
    private lateinit var adapter: RecipeAdapter

    // Data dummy lengkap untuk simulasi pencarian
    // Nanti Anggota 2 ganti ini dengan pemanggilan API Retrofit
    private val allDummyRecipes = listOf(
        Recipe("1", "Ayam Goreng Crispy", "Chicken",
            "https://www.themealdb.com/images/media/meals/tyywsw1505930373.jpg",
            "Marinasi ayam dengan bumbu. Celupkan ke kocokan telur lalu baluri tepung. Goreng hingga keemasan."),
        Recipe("2", "Soto Ayam", "Chicken",
            "https://www.themealdb.com/images/media/meals/tvttqv1511181488.jpg",
            "Rebus ayam bersama serai, daun salam, dan jahe. Tumis bumbu halus lalu masukkan ke kaldu. Sajikan dengan mie dan pelengkap."),
        Recipe("3", "Calamari Goreng", "Seafood",
            "https://www.themealdb.com/images/media/meals/xxrxux1503783827.jpg",
            "Bersihkan cumi dan potong cincin. Baluri tepung bumbu. Goreng dalam minyak panas hingga kuning keemasan."),
        Recipe("4", "Udang Saus Padang", "Seafood",
            "https://www.themealdb.com/images/media/meals/wrssvt1511556563.jpg",
            "Tumis bumbu saus padang hingga harum. Masukkan udang yang sudah dibersihkan. Masak hingga matang dan saus mengental."),
        Recipe("5", "Coklat Lava Cake", "Dessert",
            "https://www.themealdb.com/images/media/meals/tbttqo1515997275.jpg",
            "Lelehkan coklat dan butter. Kocok telur dan gula hingga mengembang. Campur semua bahan, tuang ke cetakan, panggang 10 menit."),
        Recipe("6", "Es Krim Vanilla", "Dessert",
            "https://www.themealdb.com/images/media/meals/uvsyxu1487327884.jpg",
            "Campur susu, krim, gula, dan ekstrak vanilla. Masak hingga mengental. Dinginkan lalu bekukan semalam."),
        Recipe("7", "Ayam Bakar Madu", "Chicken",
            "https://www.themealdb.com/images/media/meals/1520082781.jpg",
            "Marinasi ayam dengan kecap, madu, dan bawang putih. Bakar di atas bara api sambil terus dioles bumbu."),
        Recipe("8", "Ikan Bakar Jimbaran", "Seafood",
            "https://www.themealdb.com/images/media/meals/wrssvt1511556563.jpg",
            "Lumuri ikan dengan bumbu Bali. Bakar di atas arang hingga matang. Sajikan dengan sambal matah dan lalapan."),
        Recipe("9", "Pudding Coklat", "Dessert",
            "https://www.themealdb.com/images/media/meals/tbttqo1515997275.jpg",
            "Didihkan susu bersama agar-agar dan coklat bubuk. Tambahkan gula secukupnya. Tuang ke cetakan dan dinginkan."),
        Recipe("10", "Rendang Ayam", "Chicken",
            "https://www.themealdb.com/images/media/meals/tvttqv1511181488.jpg",
            "Tumis bumbu rendang hingga harum. Masukkan ayam dan santan. Masak dengan api kecil hingga santan mengering dan bumbu meresap.")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi semua view
        etSearch        = view.findViewById(R.id.etSearch)
        btnSearch       = view.findViewById(R.id.btnSearch)
        progressBarSearch = view.findViewById(R.id.progressBarSearch)
        rvSearchResult  = view.findViewById(R.id.rvSearchResult)
        layoutEmpty     = view.findViewById(R.id.layoutEmpty)
        layoutInitial   = view.findViewById(R.id.layoutInitial)
        tvResultInfo    = view.findViewById(R.id.tvResultInfo)
        tvEmptyTitle    = view.findViewById(R.id.tvEmptyTitle)
        tvEmptySubtitle = view.findViewById(R.id.tvEmptySubtitle)

        setupRecyclerView()
        setupSearchListener()
    }

    private fun setupRecyclerView() {
        adapter = RecipeAdapter(emptyList()) { recipe ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.idMeal)
            intent.putExtra("RECIPE_NAME", recipe.strMeal)
            intent.putExtra("RECIPE_CATEGORY", recipe.strCategory)
            intent.putExtra("RECIPE_IMAGE", recipe.strMealThumb)
            intent.putExtra("RECIPE_INSTRUCTION", recipe.strInstructions)
            startActivity(intent)
        }

        rvSearchResult.layoutManager = LinearLayoutManager(requireContext())
        rvSearchResult.adapter = adapter
    }

    private fun setupSearchListener() {

        // Klik tombol panah search
        btnSearch.setOnClickListener {
            performSearch()
        }

        // Tekan tombol "Search" / "Enter" di keyboard
        etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun performSearch() {
        val keyword = etSearch.text.toString().trim()

        // Validasi input kosong
        if (keyword.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Masukkan nama makanan dulu ya! 🍳",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Sembunyikan keyboard
        hideKeyboard()

        // Tampilkan loading
        showState(State.LOADING)

        // Simulasi delay loading (nanti Anggota 2 ganti dengan Retrofit call)
        // Di sini pakai Handler buat simulate network delay
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            if (isAdded) { // pastikan fragment masih aktif
                val results = searchFromDummy(keyword)
                if (results.isEmpty()) {
                    showState(State.EMPTY)
                    tvEmptyTitle.text = "Resep \"$keyword\" tidak ditemukan"
                    tvEmptySubtitle.text = "Coba kata kunci lain seperti Ayam, Ikan, atau Coklat"
                } else {
                    adapter.updateData(results)
                    tvResultInfo.text = "Ditemukan ${results.size} resep untuk \"$keyword\""
                    showState(State.RESULT)
                }
            }
        }, 800) // delay 800ms simulasi loading
    }

    // Fungsi pencarian dari data dummy
    // Nanti Anggota 2 ganti fungsi ini dengan Retrofit API call
    private fun searchFromDummy(keyword: String): List<Recipe> {
        return allDummyRecipes.filter { recipe ->
            recipe.strMeal.contains(keyword, ignoreCase = true) ||
                    recipe.strCategory.contains(keyword, ignoreCase = true)
        }
    }

    // Enum state untuk mengatur tampilan
    private enum class State {
        INITIAL, LOADING, RESULT, EMPTY
    }

    // Fungsi utama yang mengatur visibility semua komponen
    private fun showState(state: State) {
        layoutInitial.visibility     = if (state == State.INITIAL)  View.VISIBLE else View.GONE
        progressBarSearch.visibility = if (state == State.LOADING)  View.VISIBLE else View.GONE
        rvSearchResult.visibility    = if (state == State.RESULT)   View.VISIBLE else View.GONE
        layoutEmpty.visibility       = if (state == State.EMPTY)    View.VISIBLE else View.GONE
        tvResultInfo.visibility      = if (state == State.RESULT)   View.VISIBLE else View.GONE
    }

    // Sembunyikan keyboard setelah search
    private fun hideKeyboard() {
        val imm = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
    }
}