package com.lab06.presentation.apartments

import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lab06.R
import com.lab06.databinding.ItemApartmentBinding
import com.lab06.logic.Apartment

class AdapterApartment(val context: Context) :
    RecyclerView.Adapter<AdapterApartment.ViewHolder>() {

    var items = listOf<Apartment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: ItemApartmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun render(apartment: Apartment) {
            binding.apartmentDescription.text = apartment.description
            binding.apartmentPrice.text = ("" + apartment.price)
            if (apartment.imageBM != null)
                binding.rImg.setImageBitmap(apartment.imageBM)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemApartmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(items[position])
        holder.itemView.setOnClickListener {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString("phonenumber", items[position].landlord.phone)
            editor.putString("lat", items[position].latitude.toString())
            editor.putString("lng", items[position].longitude.toString())
            editor.commit()
            val bundle = bundleOf(
                "apartment" to items[position]
            )
            holder.itemView.findNavController().navigate(R.id.apartmentDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = items.size
}