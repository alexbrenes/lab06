package com.lab06.presentation.contact

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.telephony.SmsManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.lab06.R
import com.lab06.databinding.FragmentApartmentsBinding
import com.lab06.databinding.FragmentContactBinding
import com.lab06.logic.Apartment
import com.lab06.logic.Landlord

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE
            ),
            1
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            context
        )
        val phone = sharedPreferences.getString("phonenumber", "86942012")
        binding.phoneNumber.setText(phone)
        return binding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        val callBtn = binding.callBtn
        val txtsms = binding.smsContent
        val sendSMSBtn = binding.sendSMSBtn
        callBtn.setOnClickListener {
            callPhoneNumber()
        }
        sendSMSBtn.setOnClickListener {

            val txtMobile = binding.phoneNumber.text.toString()
            if (txtsms.text.toString().isEmpty()) {
                Toast.makeText(activity, "No empty messages!", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val smgr = SmsManager.getDefault()
                    smgr.sendTextMessage(
                        txtMobile,
                        null,
                        txtsms.text.toString(),
                        null,
                        null
                    )
                    Toast.makeText(activity, "SMS Sent Successfully", Toast.LENGTH_SHORT)
                        .show()
                } catch (e: Exception) {
                    Toast.makeText(
                        activity,
                        "SMS Failed to Send, Please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("PROBLEMA",e.toString())
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber()
            }
        }
    }

    fun callPhoneNumber() {
        try {
            val txtPhone = binding.phoneNumber.text.toString()
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CALL_PHONE),
                        101
                    )
                    return
                }
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + txtPhone)
                startActivity(callIntent)
            } else {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + txtPhone)
                startActivity(callIntent)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}