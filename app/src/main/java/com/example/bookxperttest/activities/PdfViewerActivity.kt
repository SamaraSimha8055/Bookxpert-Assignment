package com.example.bookxperttest.activities

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookxperttest.R

class PdfViewerActivity : AppCompatActivity() {

    private val pdfUrl = "https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        val webView = findViewById<WebView>(R.id.webViewPdf)

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true

        // Use Google Docs viewer to open online PDF
        val googleViewerUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"
        webView.loadUrl(googleViewerUrl)
    }
}
