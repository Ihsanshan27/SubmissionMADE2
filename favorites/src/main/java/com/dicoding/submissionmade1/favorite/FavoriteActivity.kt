package com.dicoding.submissionmade1.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.submissionmade1.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}