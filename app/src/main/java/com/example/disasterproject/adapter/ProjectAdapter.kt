package com.example.disasterproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.disasterproject.R
import com.example.disasterproject.object_item.Resource

class ProjectAdapter(
    private var mList :ArrayList<Resource>,
    private val onItemClick:(Resource)->Unit
    ):RecyclerView.Adapter<ProjectAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val logoImage : ImageView =itemView.findViewById(R.id.resCatImage)
        val resType : TextView = itemView.findViewById(R.id.resCatName)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resource_item,parent,false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.logoImage.setImageResource(mList[position].logo)
        holder.resType.text=mList[position].categoryName
        holder.itemView.setOnClickListener{onItemClick(mList[position])}
    }
    fun removeItem(position: Int) {
        if (position >= 0 && position < mList.size) {
            mList.removeAt(position)
            notifyItemRemoved(position)
        }
    }


}