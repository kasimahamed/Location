package com.example.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_otp).setOnClickListener {
            login()
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onVerificationFailed(Exception: FirebaseException) {
                Log.d("GFG", "onVerificationFailed  $Exception")
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                val storedVerificationId: String = verificationId
                val resendToken: PhoneAuthProvider.ForceResendingToken
                val intent = Intent(applicationContext, Otp::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                finish()
                Log.d("GFG", "onCodeSent: $verificationId")
            }
        }
    }

    private fun login(): String?{
        var number: String = " "
        number = findViewById<EditText>(R.id.et_phone_number).text.trim().toString()
        if (number.isNotEmpty()) {
            number = "+91$number"
            sendVerificationCode(number)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
        return number
    }

    private fun sendVerificationCode(number: String): FirebaseAuth {
        val auth = FirebaseAuth.getInstance()
        val options: PhoneAuthOptions? = auth.let {
            callbacks?.let { place ->
                PhoneAuthOptions.newBuilder(it)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(place)
                        .build()
            }
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        Log.d("GFG", "Auth started")
        return auth
    }
}