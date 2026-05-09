package com.example.recycleview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.Fruit
import com.example.recycleview.FruitAdapter

class MainActivity : AppCompatActivity() {
    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initFruits()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(fruitList)
        recyclerView.adapter = adapter
    }

    private fun initFruits() {
            repeat(1) {
                fruitList.add(Fruit("Apple", R.drawable.apple_pic))
                fruitList.add(Fruit("Banana", R.drawable.banana_pic))
                fruitList.add(Fruit("Orange", R.drawable.orange_pic))
                fruitList.add(Fruit("Watermelon", R.drawable.watermelon_pic))
                fruitList.add(Fruit("Pear", R.drawable.pear_pic))
                fruitList.add(Fruit("Grape", R.drawable.grape_pic))
                fruitList.add(Fruit("Pineapple", R.drawable.pineapple_pic))
                fruitList.add(Fruit("Strawberry", R.drawable.strawberry_pic))
                fruitList.add(Fruit("Cherry", R.drawable.cherry_pic))
                fruitList.add(Fruit("Mango", R.drawable.mango_pic))
                fruitList.add(Fruit("Car", R.drawable.car))
                fruitList.add(Fruit("2510717129叶锦维", R.drawable.headimg))
            }
    }
    private fun getRandomLengthName(name: String): String {
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n) {
            builder.append(name)
        }
        return builder.toString()
    }
}