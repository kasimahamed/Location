package com.example.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.main.dataclass.LocationDataClass
import com.google.firebase.database.*

class LocationDataAdapter(private val datalist: ArrayList<LocationDataClass>) : RecyclerView.Adapter<LocationDataAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val placesview = LayoutInflater.from(parent.context).inflate(R.layout.unic_item, parent, false)
        return MyViewHolder(placesview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val placesdata : LocationDataClass = datalist[position]

        holder.name.text = placesdata.name
        holder.disc.text = placesdata.disc
        holder.delete_btn.setOnClickListener {
            deletePlacesData(placesdata)
        }
        holder.view_btn.setOnClickListener {
            val intent = Intent(it.context, SecondMapp::class.java)
            it.context.startActivity(intent)
        }
    }

    private fun deletePlacesData(placedata: LocationDataClass): DatabaseReference {
        val delete_ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Datas")
        placedata.id?.let { delete_ref.child(it).removeValue() }
        return delete_ref
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    class MyViewHolder(placeview: View) : RecyclerView.ViewHolder(placeview) {
        val name: TextView = itemView.findViewById(R.id.name_tv)
        val disc: TextView = itemView.findViewById(R.id.disc_tv)
        val delete_btn: Button = itemView.findViewById(R.id.delete_btn)
        val view_btn: Button = itemView.findViewById(R.id.view_btn)
    }
}



