package com.example.bookxperttest.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookxperttest.R
import java.io.File

class ImagePickerActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private val pickImage = 101
    private val captureImage = 102
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
    )
    private val PERMISSION_REQUEST_CODE = 123
    private var lastAction: String? = null


    private lateinit var cameraImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)

        imageView = findViewById(R.id.imageViewResult)

        findViewById<Button>(R.id.btnPickFromGallery).setOnClickListener {
            if (hasAllPermissions()) {
                pickFromGallery()
            } else {
                lastAction = "GALLERY"
                requestPermissions()
            }
        }

        findViewById<Button>(R.id.btnCaptureImage).setOnClickListener {
            if (hasAllPermissions()) {
                captureFromCamera()
            } else {
                lastAction = "CAMERA"
                requestPermissions()
            }
        }
    }


    private fun hasAllPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        val shouldShowRationale = REQUIRED_PERMISSIONS.any {
            ActivityCompat.shouldShowRequestPermissionRationale(this, it)
        }

        if (shouldShowRationale) {
            // Optional: show a custom dialog before asking again
            AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("This app needs Camera and Storage access to pick or capture images.")
                .setPositiveButton("Allow") { _, _ ->
                    ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE)
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            // Direct request
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                when (lastAction) {
                    "GALLERY" -> pickFromGallery()
                    "CAMERA" -> captureFromCamera()
                }
            } else {
                val permanentlyDenied = permissions.any { permission ->
                    !ActivityCompat.shouldShowRequestPermissionRationale(this, permission) &&
                            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
                }

                if (permanentlyDenied) {
                    AlertDialog.Builder(this)
                        .setTitle("Permission required")
                        .setMessage("Please enable permissions manually from settings.")
                        .setPositiveButton("Go to Settings") { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, pickImage)
    }

    private fun captureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imageFile = File.createTempFile("captured_", ".jpg", cacheDir)
        cameraImageUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", imageFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri)
        startActivityForResult(intent, captureImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                pickImage -> {
                    val uri = data?.data
                    imageView.setImageURI(uri)
                }
                captureImage -> {
                    imageView.setImageURI(cameraImageUri)
                }
            }
        }
    }
}