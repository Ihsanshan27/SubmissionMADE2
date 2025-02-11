package com.dicoding.submissionmade1.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.submissionmade1.R
import com.dicoding.submissionmade1.core.domain.model.Events
import com.dicoding.submissionmade1.core.utils.ResultState
import com.dicoding.submissionmade1.databinding.ActivityDetailBinding
import com.dicoding.submissionmade1.ui.loadImage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailActivityViewModel by viewModel()

    private var favorite: Boolean = false

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.detail_event)
            setDisplayHomeAsUpEnabled(true)
        }

        val itemId = intent.getIntExtra(EXTRA_ID, 0)

        lifecycleScope.launch {
            viewModel.detailEvent.collect { result ->
                when (result) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        showLoading(false)
                        setDetailEvent(result.data)
                    }

                    is ResultState.Error -> {
                        showToast(result.error)
                        showLoading(false)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getFavoriteById(itemId.toString()).collect { favorites ->
                favorite = favorites != null
                updateFavorite(favorite)
            }
        }

        binding.fabFavorite.setOnClickListener {
            val event = (viewModel.detailEvent.value as? ResultState.Success)?.data
            if (event != null) {
                if (favorite) {
                    viewModel.deleteFavorite(event)
                } else {
                    viewModel.addFavorite(event)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.detailEvent.value !is ResultState.Success) {
            val itemId = intent.getIntExtra(EXTRA_ID, 0)
            viewModel.getDetailEvent(itemId)
        }
    }

    private fun updateFavorite(isFavorite: Boolean) {
        binding.fabFavorite.setImageResource(
            if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        )
    }

    private fun setDetailEvent(detailEvent: Events?) {
        binding.apply {
            tvName.text = detailEvent?.name
            tvSummaryDetail.text = detailEvent?.summary
            tvQuota.text = getString(R.string.quota, detailEvent?.registrants)
            tvBegin.text = detailEvent?.beginTime.toString()
            tvOwnerName.text = detailEvent?.ownerName.toString()
            tvDescriptionDetail.text = HtmlCompat.fromHtml(
                detailEvent?.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            imgMediaCover.loadImage(detailEvent?.mediaCover)

            btnRegistrasi.setOnClickListener {
                val link = detailEvent?.link
                if (link != null) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@DetailActivity,
                        "Url tidak tersedia",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_ID = "event.id"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

