package com.example.main.Views


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.main.Data_Class.Save_Data_class
import com.example.main.R
import com.google.firebase.database.FirebaseDatabase

class Name_Discription_view : AppCompatActivity() {

    private var name_et: EditText? = null
    private var disc_et: EditText? = null
    private var save_btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        name_et = findViewById(R.id.name_et)
        disc_et = findViewById(R.id.disc_et)
        save_btn = findViewById(R.id.save_btn)

        save_btn?.setOnClickListener {
            saveDatas()
        }
    }

    private fun saveDatas() {
        val name = name_et?.text.toString().trim()
        val discription = disc_et?.text.toString().trim()
        val latitude = intent.getDoubleExtra("Lat", 0.0)
        Toast.makeText(this, "Latitude Is :" + latitude, Toast.LENGTH_SHORT).show()
        val longitude = intent.getDoubleExtra("Lng", 0.0)
        Toast.makeText(this, "Longitude Is :" + longitude, Toast.LENGTH_SHORT).show()

        if (name.isEmpty() && discription.isEmpty() && latitude != null && longitude != null) {
            name_et?.error = "Please Enter Name"
            return
        }
        val ref = FirebaseDatabase.getInstance().getReference("Datas")
        val dataId = ref.push().key
        val data = Save_Data_class(dataId!!, name, discription, latitude, longitude)

        ref.child(dataId).setValue(data).addOnCompleteListener {
            Toast.makeText(applicationContext, "Data's Saved Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlacesList_Data_view::class.java)
            startActivity(intent)
        }
    }
}