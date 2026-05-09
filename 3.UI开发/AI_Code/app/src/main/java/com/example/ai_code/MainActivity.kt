package com.example.ai_code

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var resultText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var pickButton: Button
    private lateinit var sampleButtons: List<Button>
    private lateinit var samples: List<SampleImageFactory.SampleImage>

    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val bitmap = loadBitmapFromUri(uri)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                runTextRecognition(InputImage.fromFilePath(this, uri))
            } else {
                resultText.text = "Failed to load image."
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.source_image)
        resultText = findViewById(R.id.result_text)
        progressBar = findViewById(R.id.progress_bar)
        pickButton = findViewById(R.id.button_pick)

        samples = SampleImageFactory.buildSamples(this)
        sampleButtons = listOf(
            findViewById(R.id.button_sample_1),
            findViewById(R.id.button_sample_2),
            findViewById(R.id.button_sample_3),
            findViewById(R.id.button_sample_4),
            findViewById(R.id.button_sample_5)
        )

        sampleButtons.forEachIndexed { index, button ->
            val label = samples.getOrNull(index)?.label ?: "Sample"
            button.text = "Sample ${index + 1} ($label)"
            button.setOnClickListener { showSample(index) }
        }

        pickButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        if (samples.isNotEmpty()) {
            showSample(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textRecognizer.close()
    }

    private fun showSample(index: Int) {
        val sample = samples.getOrNull(index) ?: return
        imageView.setImageBitmap(sample.bitmap)
        runTextRecognition(InputImage.fromBitmap(sample.bitmap, 0))
    }

    private fun runTextRecognition(image: InputImage) {
        setUiBusy(true)
        resultText.text = "Running OCR..."
        textRecognizer.process(image)
            .addOnSuccessListener { result ->
                resultText.text = if (result.text.isBlank()) {
                    "No text detected."
                } else {
                    result.text
                }
            }
            .addOnFailureListener { error ->
                resultText.text = "OCR failed: ${error.message ?: "Unknown error"}"
            }
            .addOnCompleteListener {
                setUiBusy(false)
            }
    }

    private fun setUiBusy(isBusy: Boolean) {
        progressBar.visibility = if (isBusy) View.VISIBLE else View.GONE
        pickButton.isEnabled = !isBusy
        sampleButtons.forEach { it.isEnabled = !isBusy }
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
        } catch (ex: Exception) {
            null
        }
    }
}