package com.example.disasterproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.disasterproject.R
import com.example.disasterproject.object_item.LocationModel
import com.example.disasterproject.object_item.StepsModel

class StepsAdapter(private val mList: ArrayList<StepsModel>):RecyclerView.Adapter<StepsAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
       val steps :TextView=itemView.findViewById(R.id.stepsTV)
       fun bindView(std:StepsModel) {
           steps.text = std.steps
       }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_steps,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =mList[position]
        holder.bindView(currentItem)
    }
}

