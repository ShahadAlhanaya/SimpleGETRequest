package com.example.simplegetrequest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewButton = findViewById<Button>(R.id.btn_textViewResult)
        val recyclerViewButton = findViewById<Button>(R.id.btn_recyclerResult)

        textViewButton.setOnClickListener {
            startActivity(Intent(this,TextViewActivity::class.java))
        }

        recyclerViewButton.setOnClickListener {
            startActivity(Intent(this,RecyclerViewActivity::class.java))
        }
    }

}