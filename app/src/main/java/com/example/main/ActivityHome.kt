package com.example.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityHome : AppCompatActivity() {

        val mark_btn by lazy { findViewById<Button>(R.id.mark_btn) }
        val view_btn by lazy { findViewById<Button>(R.id.view_btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mark_btn.setOnClickListener()
       {
           val intent = Intent(this, FirstMapp::class.java)
           startActivity(intent)
       }

        view_btn.setOnClickListener()
       {
            val intent = Intent(this, PlacesListDataview::class.java)
            startActivity(intent)
       }
    }
}



