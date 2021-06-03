package com.example.main.RecyclerView_Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.main.Data_Class.Location_Data_class
import com.example.main.Map_Activities.Second_Mapp
import com.example.main.R
import com.google.firebase.database.*

class Location_Data_Adapter(private val datalist: ArrayList<Location_Data_class>) : RecyclerView.Adapter<Location_Data_Adapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val placesview = LayoutInflater.from(parent.context).inflate(R.layout.unic_item, parent, false)
        return MyViewHolder(placesview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val placesdata = datalist[position]

        holder.name.text = placesdata.name
        holder.disc.text = placesdata.disc
        holder.delete_btn.setOnClickListener {
            deletePlacesData(placesdata)
        }
        holder.view_btn.setOnClickListener {
            val intent = Intent(it.context, Second_Mapp::class.java)
            it.context.startActivity(intent)
        }
    }

    private fun deletePlacesData(placedata: Location_Data_class) {
        val delete_ref = FirebaseDatabase.getInstance().getReference("Datas")
        placedata.id?.let { delete_ref.child(it).removeValue() }
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



