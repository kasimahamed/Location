package com.example.main.Home_Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.main.Map_Activities.First_Mapp
import com.example.main.Views.PlacesList_Data_view
import com.example.main.R

class Activity_Home : AppCompatActivity() {

        val mark_btn by lazy { findViewById<Button>(R.id.mark_btn) }
        val view_btn by lazy { findViewById<Button>(R.id.view_btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mark_btn.setOnClickListener()
       {
           val intent = Intent(this, First_Mapp::class.java)
           startActivity(intent)
       }

        view_btn.setOnClickListener()
       {
            val intent = Intent(this, PlacesList_Data_view::class.java)
            startActivity(intent)
       }
    }
}



