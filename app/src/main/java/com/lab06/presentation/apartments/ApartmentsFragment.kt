package com.lab06.presentation.apartments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lab06.R
import com.lab06.databinding.FragmentApartmentsBinding


class ApartmentsFragment : Fragment() {

    private var _binding: FragmentApartmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var apartmentsViewModel: ApartmentsViewModel
    private lateinit var adapter: AdapterApartment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        apartmentsViewModel = ApartmentsViewModel()
        _binding = FragmentApartmentsBinding.inflate(inflater, container, false)
        recyclerView = binding.rvApartments
        recyclerView.setHasFixedSize(true)

        val llm = LinearLayoutManager(activity)
        llm.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = llm


        return binding.root
    }

    private fun initRecyclerView(): AdapterApartment {
        val adapter = AdapterApartment(requireContext())
        binding.rvApartments.adapter = adapter
        return adapter
    }

    private fun OnInitViewmodel(adapter: AdapterApartment) {
        apartmentsViewModel.apartments.items.observe(viewLifecycleOwner) { items ->
            adapter.items = items
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView().let { adapter ->
            OnInitViewmodel(adapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}