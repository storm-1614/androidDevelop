package com.example.emptyviewactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.show()

        supportActionBar?.title = "Project"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val first_button : Button = findViewById<Button>(R.id.first_button)
        first_button.setOnClickListener {
            Toast.makeText(this,"You clicked first button", Toast.LENGTH_SHORT ).show()
//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:2510717129"))
            val intent = Intent(this, secondActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_item -> Toast.makeText(this, "You clicked add", Toast.LENGTH_SHORT).show()
            R.id.name_item -> Toast.makeText(this, "You clicked name", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
