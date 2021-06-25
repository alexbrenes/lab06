package com.lab06.presentation.publish

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.lab06.R
import com.lab06.databinding.FragmentContactBinding
import com.lab06.databinding.FragmentPublishApartmentBinding
import com.lab06.logic.Apartment
import com.lab06.logic.Landlord

class PublishApartmentFragment : Fragment() {

    private val publishViewModel: PublishViewModel
    val Image_Capture_Code = 1
    var imgCapture: ImageView? = null
    var bitmap: Bitmap? = null
    private var _binding: FragmentPublishApartmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    init {
        publishViewModel = PublishViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPublishApartmentBinding.inflate(inflater, container, false)
        imgCapture = binding.apartmentPImage
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {//cambio
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_Capture_Code) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val bp = data!!.extras!!["data"] as Bitmap?//cambio
                imgCapture!!.setImageBitmap(bp)
                bitmap = bp
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val addPhotoBtn = binding.addPhotosBtn
        addPhotoBtn.setOnClickListener {
            val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cInt, Image_Capture_Code)
        }
        val publishBtn = binding.publishBtn
        val setLocation = binding.setLocation

        setLocation.setOnClickListener {
            requireView().findNavController().navigate(R.id.setLocationFragment)
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            context
        )
        val latNew = sharedPreferences.getString("latNew", "666")
        val lngNew = sharedPreferences.getString("lngNew", "666")
        val landlordPname = binding.landlordPname
        val landlordPphone = binding.landlordPphone
        val landlordPdesc = binding.landlordPdesc
        val landlordPprice = binding.landlordPprice
        val landlordPlatitude = binding.landlordPlatitude
        val landlordPlongitude = binding.landlordPlongitude
        landlordPlatitude.setText(latNew)
        landlordPlongitude.setText(lngNew)
        publishBtn.setOnClickListener {
            if (!landlordPname.text.toString().isEmpty()
                &&
                !landlordPphone.text.toString().isEmpty()
                &&
                !landlordPdesc.text.toString().isEmpty()
                &&
                !landlordPprice.text.toString().isEmpty()
                &&
                !landlordPlatitude.text.toString().isEmpty()
                &&
                !landlordPlongitude.text.toString().isEmpty()
            ) {
                val landlord =
                    Landlord(landlordPname.text.toString(), landlordPphone.text.toString())
                val apartment = Apartment(
                    1,
                    landlordPlatitude.text.toString().toDouble(),
                    landlordPlongitude.text.toString().toDouble(),
                    landlordPdesc.text.toString(),
                    landlord,
                    landlordPprice.text.toString().toFloat(),
                    bitmap
                )
                publishViewModel.add(apartment)
                Toast.makeText(
                    context,
                    "Apartment published succesfully",
                    Toast.LENGTH_SHORT
                ).show()
                landlordPname.setText("")
                landlordPphone.setText("")
                landlordPdesc.setText("")
                landlordPprice.setText("")
                landlordPlatitude.setText("")
                landlordPlongitude.setText("")
            } else {
                Toast.makeText(
                    context,
                    "Check the input fields!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}