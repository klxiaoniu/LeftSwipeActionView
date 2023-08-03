package com.test.leftswipeactionview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.leftswipeactionview.view.SwipeActionView

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var data: List<String> = emptyList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sav = itemView.findViewById<SwipeActionView>(R.id.sav)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<String>) {
        data = newData
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        val list = data.toMutableList()
        list.removeAt(position)
        data = list
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_swipe_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.sav.recover()    // 恢复原状，防止复用时出现问题
        holder.sav.text = data[position]
        holder.sav.callback = object : SwipeActionView.Callback {
            override fun onSwipeFinished() {
                removeData(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount() = data.size
}