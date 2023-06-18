package com.india.testingapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.india.testingapp.databinding.UserInfoLayoutBinding
import com.india.testingapp.models.response.Data

class UserAdapter(private val dataList:List<Data>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    class ViewHolder(val binding : UserInfoLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserInfoLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userName.text = "${dataList[position].first_name} ${dataList[position].last_name}"
        Glide.with(holder.binding.root.context).load(dataList[position].avatar).into(holder.binding.userImage);
    }

}