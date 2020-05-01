package com.example.latihanroom2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanroom2.R
import com.example.latihanroom2.entities.UserEntity
import kotlinx.android.synthetic.main.rv_itemview_user.view.*

class UserAdater(val items:List<UserEntity>, val context:Context): RecyclerView.Adapter<UserAdater.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nama = view.tv_nama_rvItemViewUser
        val umur = view.tv_umur_rvItemViewUser
        val id = view.tv_id_rvItemViewUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.rv_itemview_user,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nama.text = items.get(position).nama
        holder.umur.text = items.get(position).umur
        holder.id.text = items.get(position).id.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}