package com.dicoding.submissionmade1.ui.finished

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
import com.dicoding.submissionmade1.databinding.FragmentFinishedBinding
import com.dicoding.submissionmade1.ui.adapter.EventsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private val finishedViewModel: FinishedViewModel by viewModel()
    private lateinit var finishedAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finishedAdapter = EventsAdapter()
        binding.rvVertical.adapter = finishedAdapter
        binding.rvVertical.layoutManager = LinearLayoutManager(requireActivity())

        lifecycleScope.launch {
            finishedViewModel.listFinishedEvents.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        setupFinishedEvents(state.data)
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

    override fun onResume() {
        super.onResume()
        if (finishedViewModel.listFinishedEvents.value !is ResultState.Success) {
            finishedViewModel.getFinishedEvents()
        }
    }

    private fun setupFinishedEvents(finishedEvents: List<Events?>) {
        finishedAdapter.submitList(finishedEvents)
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