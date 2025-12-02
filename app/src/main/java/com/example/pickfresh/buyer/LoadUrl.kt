package com.example.pickfresh.buyer

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.pickfresh.databinding.ActivityLoadUrlBinding

class LoadUrl : AppCompatActivity() {
    private val bind by lazy {
        ActivityLoadUrlBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        intent.getStringExtra("urlPoint")?.let {
            bind.webLoader.apply {
                loadUrl(it)
                settings.javaScriptEnabled=true
                webViewClient= WebViewClient()
            }

        }

    }
}