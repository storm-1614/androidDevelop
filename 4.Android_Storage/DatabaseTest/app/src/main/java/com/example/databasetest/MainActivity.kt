package com.example.databasetest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.widget.Toast
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
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 3)
        val createDatabase = findViewById<Button>(R.id.createDatabase)
        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

        val addData = findViewById<Button>(R.id.addData)
        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                // 开始组装第一条数据
                put("学号", "2510717129")
                put("姓名", "叶锦维")
                put("性别", "男")
            }
            db.insert("Book", null, values1)
            val values2 = ContentValues().apply {
                // 开始组装第一条数据
                put("学号", "31415926")
                put("姓名", "小明")
                put("性别", "男")
            }
            db.insert("Book", null, values2)
            Toast.makeText(this, "Insert succeeded", Toast.LENGTH_SHORT).show()  // 弹出 Toast 提示建表成功
        }

        val updateData = findViewById<Button>(R.id.updateData)
        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("姓名", "叶锦维（已更新）")
            val rows = db.update("Book", values, "姓名 = ?", arrayOf("叶锦维"))
            Toast.makeText(this, "更新了 $rows 条记录", Toast.LENGTH_SHORT).show()
        }

        val deleteData = findViewById<Button>(R.id.deleteData)
        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "姓名 = ?", arrayOf("叶锦维"))
            Toast.makeText(this, "已删除姓名为'叶锦维'的记录", Toast.LENGTH_SHORT).show()
        }

        val queryData = findViewById<Button>(R.id.queryData)
        queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("Book", null, "姓名 = ?", arrayOf("叶锦维"), null, null, null)
            if (cursor.moveToFirst()) {
                val result = StringBuilder()
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val studentId = cursor.getInt(cursor.getColumnIndexOrThrow("学号"))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("姓名"))
                    val gender = cursor.getString(cursor.getColumnIndexOrThrow("性别"))
                    val line = "id=$id 学号=$studentId 姓名=$name 性别=$gender"
                    result.append("$line\n")
                    Log.d("DatabaseTest", line)
                } while (cursor.moveToNext())
                cursor.close()
                Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
            } else {
                Log.d("DatabaseTest", "没有查到数据")
                Toast.makeText(this, "没有查到数据", Toast.LENGTH_SHORT).show()
                cursor.close()
            }
        }
    }
}

class MyDatabaseHelper(val context: Context, name:String, version:Int) : SQLiteOpenHelper(context, name, null, version) {
        private val createBook = "CREATE TABLE Book (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "学号 INTEGER," +
                "姓名 TEXT," +
                "性别 TEXT)"



//    数据库首次创建时调用
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBook)   // 执行建表 SQL
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()  // 弹出 Toast 提示建表成功
    }

    // 数据库版本升级时调用
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Book")  // 删旧表
        onCreate(db)                             // 重建
        Toast.makeText(context, "Upgrade succeeded", Toast.LENGTH_SHORT).show()  // 弹出 Toast 提示建表成功
    }
}