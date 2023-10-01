package com.example.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var generateQRButton: Button
    private lateinit var scanQRButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var resultImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateQRButton = findViewById(R.id.generateQRButton)
        scanQRButton = findViewById(R.id.scanQRButton)
        resultTextView = findViewById(R.id.resultTextView)

        generateQRButton.setOnClickListener {
            val content = "This is the data you want to share"
            generateQRCode(content)
        }
        scanQRButton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.setBeepEnabled(true)
            integrator.initiateScan()
        }

    }

    private fun generateQRCode(content: String) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400)
            resultTextView.text = ""
            resultImageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                resultTextView.text = "Scanned Content: ${result.contents}"
            } else {
                resultTextView.text = "Scan canceled"
            }
        }
    }
}