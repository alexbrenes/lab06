package com.lab06.presentation.apartment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lab06.R
import com.lab06.databinding.FragmentApartmentDetailBinding
import com.lab06.logic.Apartment
import com.lab06.logic.Landlord

class ApartmentDetailFragment : Fragment() {

    private var _binding: FragmentApartmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var apartment: Apartment
    private lateinit var landlord: Landlord

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApartmentDetailBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        apartment = bundle?.getSerializable("apartment") as Apartment
        landlord = Landlord(apartment.landlord.name, apartment.landlord.phone)
        binding.apartmentDetailDescription.text = apartment.description
        binding.apartmentDetailPrice.text = ("" + apartment.price)
        binding.idApartment.text = ("" + apartment.id)
        binding.landlordDetailName.text = apartment.landlord.name
        binding.landlordDetailPhoneNumber.text = apartment.landlord.phone
        if (apartment.imageBM != null)
            binding.ivApartment.setImageBitmap(apartment.imageBM)
        val contactBtn = binding.contactBtn
        contactBtn.setOnClickListener {
            val bundleLandlord = bundleOf(
                "landlord" to apartment.landlord
            )
            requireView().findNavController().navigate(R.id.contactFragment, bundleLandlord)
        }
        val videoTourBtn = binding.videoTourBtn
        videoTourBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.videoFragment)
        }
        val locateInMapBtn = binding.locateInMap
        locateInMapBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.locationFragment)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }


}