package com.dicoding.submissionmade1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.submissionmade1.core.domain.model.Events
import com.dicoding.submissionmade1.core.utils.ResultState
import com.dicoding.submissionmade1.databinding.FragmentHomeBinding
import com.dicoding.submissionmade1.ui.adapter.EventsAdapter
import com.dicoding.submissionmade1.ui.adapter.EventsHorizontalAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var upcomingAdapter: EventsHorizontalAdapter
    private lateinit var finishedAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingAdapter = EventsHorizontalAdapter()
        binding.rvHorizontal.adapter = upcomingAdapter

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvHorizontal.layoutManager = linearLayoutManager

        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(binding.rvHorizontal)

        lifecycleScope.launch {
            homeViewModel.listUpcomingEvents.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading1(true)
                    is ResultState.Success -> {
                        setupUpcomingEvents(state.data)
                        showLoading1(false)
                        showMessage(state.data.isEmpty())
                    }

                    is ResultState.Error -> {
                        showLoading1(false)
                        showToast(state.error)
                    }
                }
            }
        }

        finishedAdapter = EventsAdapter()
        binding.rvVertical.adapter = finishedAdapter
        binding.rvVertical.layoutManager = LinearLayoutManager(requireActivity())

        lifecycleScope.launch {
            homeViewModel.listFinishedEvents.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading2(true)
                    is ResultState.Success -> {
                        showLoading2(false)
                        setupFinishedEvents(state.data)
                        showMessage(state.data.isEmpty())
                    }

                    is ResultState.Error -> {
                        showLoading2(false)
                        showToast(state.error)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.listUpcomingEvents.value !is ResultState.Success) {
            homeViewModel.getupComingEvents()
        }
        if (homeViewModel.listFinishedEvents.value !is ResultState.Success) {
            homeViewModel.getFinishedEvents()
        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

    private fun showMessage(isEmpty: Boolean) {
        binding.tvInfo.apply {
            visibility = if (isEmpty) View.VISIBLE else View.GONE
            if (isEmpty) playAnimation() else cancelAnimation()
        }
    }

    private fun setupUpcomingEvents(upcomingEvents: List<Events?>) {
        upcomingAdapter.submitList(upcomingEvents)
    }

    private fun setupFinishedEvents(finishedEvents: List<Events?>) {
        finishedAdapter.submitList(finishedEvents)
    }

    private fun showLoading1(isLoading: Boolean) {
        binding.PbLoading1.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoading2(isLoading: Boolean) {
        binding.PbLoading2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}