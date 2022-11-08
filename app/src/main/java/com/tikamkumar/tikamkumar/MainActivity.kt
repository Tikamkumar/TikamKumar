package com.tikamkumar.tikamkumar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val web: WebView = findViewById(R.id.webView)
        web.webViewClient = WebViewClient()
        web.webChromeClient = WebChromeClient()

        web.apply {
            loadUrl("https://play.google.com/store/apps/developer?id=TIKAM+KUMAR&hl=en")
            settings.javaScriptEnabled = true
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (web.canGoBack()) {
                    web.goBack()
                } else {
                    finish()
                }
            }
        })
    }
}