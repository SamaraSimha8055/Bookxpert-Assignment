package com.example.bookxperttest.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookxperttest.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnPdfViewer).setOnClickListener {
            startActivity(Intent(this, PdfViewerActivity::class.java))
        }

        findViewById<Button>(R.id.btnImagePicker).setOnClickListener {
            startActivity(Intent(this, ImagePickerActivity::class.java))
        }

        findViewById<Button>(R.id.btnApiList).setOnClickListener {
            startActivity(Intent(this, ApiListActivity::class.java))
        }
    }
}
