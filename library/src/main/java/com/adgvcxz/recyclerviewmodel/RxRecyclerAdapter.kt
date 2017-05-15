package com.adgvcxz.recyclerviewmodel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adgvcxz.IAction
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel
import com.adgvcxz.bindTo
import kotlin.reflect.KClass


/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

class RxRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val noLayoutId = -1

    private var inflater: LayoutInflater? = null

    lateinit var configureCell: ((ViewModel<out IModel>) -> IView<*>)

    var items: List<ViewModel<out IModel>> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var itemClickListener: View.OnClickListener? = null

    internal val viewMap: HashMap<View, IView<ViewModel<*>>?> = HashMap()
    internal val layoutMap: HashMap<KClass<ViewModel<out IModel>>, IView<ViewModel<*>>> = HashMap()

    private lateinit var viewModel: ViewModel<out IModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        if (viewType == noLayoutId) {
            throw IllegalArgumentException("ViewHolder Model Error")
        }
        val view = inflater!!.inflate(viewType, parent, false)
        if (itemClickListener != null) {
            view.setOnClickListener(itemClickListener)
        }
        viewMap.put(view, layoutMap[viewModel::class])
        layoutMap.remove(viewModel::class)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.attach()
                .filter { it == AttachEvent.Detach }
                .map { IAction.dispose }
                .bindTo(items[position].action)

        viewMap[holder.itemView]?.bind(holder.itemView, items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        var id = layoutMap[items[position]::class]?.layoutId
        viewModel = items[position]
        if (id != null) {
            return id
        } else {
            val item = configureCell.invoke(viewModel)
            id = item.layoutId
            layoutMap.put(viewModel::class as KClass<ViewModel<out IModel>>, item as IView<ViewModel<*>>)
        }
        return id
    }
}