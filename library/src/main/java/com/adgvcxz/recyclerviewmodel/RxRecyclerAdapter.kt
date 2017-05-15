package com.adgvcxz.recyclerviewmodel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
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

    var configureCell: ((Int) -> IView<*>)? = null

    var items: List<ViewHolder<out IModel>> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var itemClickListener: View.OnClickListener? = null

    internal val viewMap: HashMap<View, IView<ViewHolder<*>>?> = HashMap()

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
        val iview: IView<ViewHolder<*>>? = configureCell?.invoke(viewType) as IView<ViewHolder<*>>?
        viewMap.put(view, iview)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.attach()
                .filter { it == AttachEvent.Detach }
                .map { IAction.dispose }
                .bindTo(items[position].action)

        viewMap[holder.itemView]?.bind(holder.itemView, items[position])

//        val iView = items[position]
//        configureCell?.invoke(holder, items[position], position)
//        if (iView.viewModel is ViewHolder<IModel>) {
//            holder.itemView.attach()
//                    .filter { it == AttachEvent.Detach }
//                    .map { IAction.dispose }
//                    .bindTo(iView.viewModel.action)
////            viewHolder.model.subscribe()
//            iView.viewModel.model.subscribe()
//            iView.bind(holder.itemView)
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].layoutId
    }

}