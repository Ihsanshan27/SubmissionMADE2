package com.dicoding.submissionmade1.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionmade1.core.domain.model.Events
import com.dicoding.submissionmade1.core.utils.ResultState
import com.dicoding.submissionmade1.databinding.FragmentSearchBinding
import com.dicoding.submissionmade1.ui.adapter.EventsAdapter
import com.dicoding.submissionmade1.ui.search.SearchViewModel.Companion.ACTIVE
import com.dicoding.submissionmade1.ui.search.SearchViewModel.Companion.QUERY
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var adapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EventsAdapter()
        binding.rvVertical.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvVertical.layoutManager = layoutManager
        binding.rvVertical.setHasFixedSize(true)

        lifecycleScope.launch {
            searchViewModel.listSearchEvents.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        showLoading(false)
                        setupSearchEvents(state.data)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(state.error)
                    }
                }
            }
        }
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                searchViewModel.searchEvents(ACTIVE, searchView.text.toString())
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (searchViewModel.listSearchEvents.value !is ResultState.Success) {
            searchViewModel.searchEvents(ACTIVE, QUERY)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.PbLoading1.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupSearchEvents(searchEvents: List<Events?>) {
        adapter.submitList(searchEvents)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}