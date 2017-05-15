package com.adgvcxz.recyclerviewmodel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.adgvcxz.IAction
import com.adgvcxz.IModel
import com.adgvcxz.bindTo


/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

class RxRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val noLayoutId = -1

    private var inflater: LayoutInflater? = null

    var items: ArrayList<in ViewHolder<IModel>> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        if (viewType == noLayoutId) {
            throw IllegalArgumentException("ViewHolder Model Error")
        }
        return object : RecyclerView.ViewHolder(inflater!!.inflate(viewType, null)){}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = items[position]
        if (viewHolder is ViewHolder<out IModel>) {
            holder.itemView.attach()
                    .filter { it == AttachEvent.Detach }
                    .map { IAction.dispose }
                    .bindTo(viewHolder.action)
            viewHolder.model.subscribe()
            viewHolder.bind(holder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val viewHolder = items[position]
        if (viewHolder is IView) {
            return viewHolder.layoutId
        }
        return noLayoutId
    }

}