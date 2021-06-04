package com.example.main


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.main.dataclass.SaveDataClass
import com.google.firebase.database.FirebaseDatabase

class NameDiscriptionview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val save_btn: Button = findViewById(R.id.save_btn)
        save_btn.setOnClickListener {
            saveDatas()
        }
    }

    private fun saveDatas(): SaveDataClass? {
        val name_et: EditText = findViewById(R.id.name_et)
        val disc_et: EditText = findViewById(R.id.disc_et)
        val name: String = name_et?.text.toString().trim()
        val discription: String = disc_et?.text.toString().trim()
        val latitude: Double = intent.getDoubleExtra("Lat", 0.0)
        Toast.makeText(this, "Latitude Is :" + latitude, Toast.LENGTH_SHORT).show()
        val longitude: Double = intent.getDoubleExtra("Lng", 0.0)
        Toast.makeText(this, "Longitude Is :" + longitude, Toast.LENGTH_SHORT).show()
        if (name.isEmpty() && discription.isEmpty() && latitude != null && longitude != null) {
            name_et?.error = "Please Enter Name"
            return null
        }
        val ref = FirebaseDatabase.getInstance().getReference("Datas")
        val dataId: String? = ref.push().key
        val data = SaveDataClass(dataId!!, name, discription, latitude, longitude)

        ref.child(dataId).setValue(data).addOnCompleteListener {
            Toast.makeText(applicationContext, "Data's Saved Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlacesListDataview::class.java)
            startActivity(intent)
        }
        return data
    }
}