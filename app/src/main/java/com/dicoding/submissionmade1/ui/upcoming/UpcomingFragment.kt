package com.dicoding.submissionmade1.ui.upcoming

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
import com.dicoding.submissionmade1.databinding.FragmentUpcomingBinding
import com.dicoding.submissionmade1.ui.adapter.EventsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val upcomingViewModel: UpcomingViewModel by viewModel()
    private lateinit var upcomingAdapter: EventsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingAdapter = EventsAdapter()
        binding.rvHorizontal.adapter = upcomingAdapter
        binding.rvHorizontal.layoutManager = LinearLayoutManager(requireActivity())

        lifecycleScope.launch {
            upcomingViewModel.listUpcomingEvents.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        setupUpcomingEvents(state.data)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(state.error)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (upcomingViewModel.listUpcomingEvents.value !is ResultState.Success) {
            upcomingViewModel.getupComingEvents()
        }
    }

    private fun setupUpcomingEvents(finishedEvents: List<Events?>) {
        upcomingAdapter.submitList(finishedEvents)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.PbLoading1.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}