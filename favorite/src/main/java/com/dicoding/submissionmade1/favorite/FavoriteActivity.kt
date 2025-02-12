package com.dicoding.submissionmade1.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionmade1.adapter.FavoriteAdapter
import com.dicoding.submissionmade1.core.domain.model.Favorite
import com.dicoding.submissionmade1.core.utils.ResultState
import com.dicoding.submissionmade1.di.favoriteModule
import com.dicoding.submissionmade1.favorite.databinding.ActivityFavoriteBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)
        supportActionBar?.apply {
            title = "Favorite Event"
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = FavoriteAdapter()
        binding.rvVertical.adapter = adapter
        binding.rvVertical.layoutManager = LinearLayoutManager(this)
        binding.rvVertical.setHasFixedSize(true)

        lifecycleScope.launch {
            favoriteViewModel.listFavorite.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        setUpListFavorites(state.data)
                        showLoading(false)
                        showNoFavoriteMessage(state.data.isEmpty())
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(state.error)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (favoriteViewModel.listFavorite.value !is ResultState.Success) {
            favoriteViewModel.getAllFavorite()
        }
    }

    private fun setUpListFavorites(favorite: List<Favorite>) {
        adapter.submitList(favorite)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNoFavoriteMessage(isEmpty: Boolean) {
        binding.tvInfo.apply {
            visibility = if (isEmpty) View.VISIBLE else View.GONE
            if (isEmpty) playAnimation() else cancelAnimation()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}