package com.example.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.dataclass.LocationDataClass
import com.google.firebase.database.*

class PlacesListDataview : AppCompatActivity() {
    private var userRecyclerView : RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataview)

        userRecyclerView = findViewById(R.id.user_list)
        userRecyclerView?.layoutManager = LinearLayoutManager(this)
        userRecyclerView?.setHasFixedSize(true)
        getUserPlaceDataData()
    }

    private fun getUserPlaceDataData(): ArrayList<LocationDataClass> {
       val userArrayList = arrayListOf<LocationDataClass>()
       val dbref = FirebaseDatabase.getInstance().getReference("Datas")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList?.clear()
                    for (dataSnapshot in snapshot.children) {
                        val user: LocationDataClass? = dataSnapshot.getValue(LocationDataClass::class.java)
                        if (user != null) {
                            userArrayList?.add(user)
                        }
                    }
                    userRecyclerView?.adapter = userArrayList?.let { LocationDataAdapter(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Fetched Data's From Database Is Fail", Toast.LENGTH_SHORT).show()
            }
        })
        return userArrayList
    }
}