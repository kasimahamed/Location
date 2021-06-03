package com.example.main.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.Data_Class.Location_Data_class
import com.example.main.R
import com.example.main.RecyclerView_Adapter.Location_Data_Adapter
import com.google.firebase.database.*

class PlacesList_Data_view : AppCompatActivity() {
    private var dbref: DatabaseReference? = null
    private var userRecyclerView: RecyclerView? = null
    private var userArrayList: ArrayList<Location_Data_class>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_view)

        userRecyclerView = findViewById(R.id.user_list)
        userRecyclerView?.layoutManager = LinearLayoutManager(this)
        userRecyclerView?.setHasFixedSize(true)
        userArrayList = arrayListOf<Location_Data_class>()

        getUserPlaceDataData()
    }

    private fun getUserPlaceDataData() {
        dbref = FirebaseDatabase.getInstance().getReference("Datas")
        dbref?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList?.clear()
                    for (dataSnapshot in snapshot.children) {
                        val user = dataSnapshot.getValue(Location_Data_class::class.java)
                        if (user != null) {
                            userArrayList?.add(user)
                        }
                    }
                    userRecyclerView?.adapter = userArrayList?.let { Location_Data_Adapter(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Fetched Data's From Database Is Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}