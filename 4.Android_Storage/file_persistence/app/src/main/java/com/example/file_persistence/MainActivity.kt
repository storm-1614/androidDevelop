package com.example.file_persistence

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inputText = load()
        if (inputText.isNotEmpty()) {
            val editText = findViewById<android.widget.EditText>(R.id.editText)
            editText.setText(inputText)
            editText.setSelection(inputText.length)
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val editText = findViewById<android.widget.EditText>(R.id.editText)
        val inputText = editText.text.toString()
        save(inputText)
    }

    private fun save(inputText: String) {
        try {
            val output = openFileOutput("data.txt", MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use { it.write(inputText) }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun load(): String {
        val content = StringBuilder()
        try {
            val input = openFileInput("data.txt")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use { reader.forEachLine {content.append(it) }}
        }catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }
}