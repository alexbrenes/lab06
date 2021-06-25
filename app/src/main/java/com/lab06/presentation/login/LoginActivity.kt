package com.lab06.presentation.login

import android.app.Dialog
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.lab06.MainActivity
import com.lab06.R
import com.lab06.databinding.ActivityLoginBinding
import com.lab06.databinding.FragmentContactBinding

class LoginActivity : AppCompatActivity() {

    private var cancellationSignal: CancellationSignal? = null
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!


    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authenthication success!")
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkBiometricSupport()
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        val authenticateBtn = findViewById<Button>(R.id.authenticateBtn)
        authenticateBtn.setOnClickListener {
            Log.d("Authentica","CLICK")
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("MyRentalApt")
                .setSubtitle("Authentication is required")
                .setDescription("Fingerprint to login MyRentalApt")
                .setNegativeButton(
                    "Cancel",
                    this.mainExecutor,
                    DialogInterface.OnClickListener { dialog, which ->
                        notifyUser("Authentication cancelled")
                    }).build()
            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)

        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("Fingerprint authentication has not been enabled in settings")
            return false
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("Fingerprint authentication permission is not enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
            true
        else false
    }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}