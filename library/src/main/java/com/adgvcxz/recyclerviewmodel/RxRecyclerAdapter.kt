package com.adgvcxz.recyclerviewmodel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.adgvcxz.ViewModel


/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

class RxRecyclerAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var inflater: LayoutInflater? = null

    var items = arrayListOf<ItemViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        return ViewHolder(inflater!!.inflate(viewType, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewModel = ViewModel(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].layoutId
    }

}