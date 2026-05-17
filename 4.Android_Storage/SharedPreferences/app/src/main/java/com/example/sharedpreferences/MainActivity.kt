package com.example.sharedpreferences

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            // 保存数据到 SharedPreferences
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.putString("姓名", "叶锦维")
            editor.putLong("学号", 2510717129)
            editor.putBoolean("married", false)
            editor.apply()

        }
        val restoreButton = findViewById<Button>(R.id.retoreButton)

        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        val name = prefs.getString("姓名", "") ?: ""
        val studentId = prefs.getLong("学号", 0L)
        val married = prefs.getBoolean("married", false)
        Log.d("MainActivity", "姓名: $name");
        Log.d("MainActivity", "学号: $studentId");
        Log.d("MainActivity", "married: $married");
    }
}