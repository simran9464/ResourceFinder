package com.example.disasterproject.adapter

import android.content.ClipData.Item
import android.health.connect.datatypes.ExerciseRoute.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.disasterproject.R
import com.example.disasterproject.object_item.LocationModel

//this class will have constructor and also the inheritance also in the recycler view fomr
class LocAdapter(
    private var mList: ArrayList<LocationModel>,
    private val onDelete:(LocationModel)-> Unit
    ):RecyclerView.Adapter<LocAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val location : TextView = itemView.findViewById(R.id.stepsTV)


        fun bindView(std:LocationModel){
            location.text = std.location
        }


        
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_location,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =mList[position]
        holder.bindView(currentItem)

//        attach swipe to delte

    }
    fun removeItem(position: Int) {
        val location = mList[position]
        onDelete(location) // Call the delete function
        mList.removeAt(position)
        notifyItemRemoved(position)
    }
}