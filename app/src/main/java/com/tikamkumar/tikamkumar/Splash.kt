package com.tikamkumar.tikamkumar

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AlertDialog
import com.tikamkumar.tikamkumar.databinding.ActivitySplashBinding
import java.util.*

class Splash : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)
        animation()

        if (checkForInternet(this)) {
            Handler(Looper.myLooper()!!).postDelayed({
                textToSpeechFunction("Most welcome")
                startActivity(Intent(this@Splash, MainActivity::class.java))
                finish()
            }, 1500)
        } else {
            val alertbuilder = AlertDialog.Builder(this)
            alertbuilder.setTitle("Alert!")
            alertbuilder.setMessage("Please check your Internet Connection.")
            alertbuilder.setCancelable(false)
            alertbuilder.setPositiveButton("OK") { _, _ ->
                finish()
                textToSpeechFunction("Please check your internet connection.")
            }
            val alertDialog = alertbuilder.create()
            alertDialog.show()
        }
    }

    private fun textToSpeechFunction(s: String) {
        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.US
        }
    }

    private fun animation() {
        binding.image.alpha = 0f
        binding.image.scaleX = 0f
        binding.image.scaleY = 0f
        binding.image.animate().apply {
            duration = 1000
            alpha(1f)
            scaleX(1f)
            scaleY(1f)
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    }
}