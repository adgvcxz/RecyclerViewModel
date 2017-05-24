package com.adgvcxz.recyclerviewmodel

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adgvcxz.IAction
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel
import com.adgvcxz.bindTo
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlin.reflect.KClass


/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

class RxRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        Consumer<Pair<List<ViewModel<out IModel>>, DiffUtil.DiffResult?>> {

    private var inflater: LayoutInflater? = null

    internal var itemClickListener: View.OnClickListener? = null

    private val viewMap: HashMap<View, IView<ViewModel<*>>?> = HashMap()

    private val layoutMap: HashMap<KClass<ViewModel<out IModel>>, IView<ViewModel<*>>> = HashMap()

    private lateinit var itemViewModel: ViewModel<out IModel>

    private val attach: Subject<Int> = PublishSubject.create<Int>().toSerialized()

    lateinit var configureCell: ((ViewModel<out IModel>) -> IView<*>)

    var animation: Boolean = false

    var viewModel: RecyclerViewModel = RecyclerViewModel()
        set(value) {
            field = value
            value.model.map { it.items }
                    .bindTo(this, animation)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater!!.inflate(viewType, parent, false)
        if (itemClickListener != null) {
            view.setOnClickListener(itemClickListener)
        }
        viewMap.put(view, layoutMap[itemViewModel::class])
        layoutMap.remove(itemViewModel::class)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.attach()
                .filter { it == AttachEvent.Detach }
                .map { IAction.dispose }
                .bindTo(viewModel.currentModel.items[position].action)

        viewMap[holder.itemView]?.bind(holder.itemView, viewModel.currentModel.items[position])
    }

    override fun getItemCount(): Int {
        return viewModel.count
    }

    override fun getItemViewType(position: Int): Int {
        var id = layoutMap[viewModel.currentModel.items[position]::class]?.layoutId
        itemViewModel = viewModel.currentModel.items[position]
        if (id != null) {
            return id
        } else {
            val item = configureCell.invoke(itemViewModel)
            id = item.layoutId
            layoutMap.put(viewModel.currentModel.items[position]::class as KClass<ViewModel<out IModel>>, item as IView<ViewModel<*>>)
        }
        return id
    }

    override fun accept(pair: Pair<List<ViewModel<out IModel>>, DiffUtil.DiffResult?>) {
        if (pair.second == null) {
            notifyDataSetChanged()
        } else {
            pair.second?.dispatchUpdatesTo(this)
        }
    }

    fun attach(): Observable<Int> {
        return attach.distinctUntilChanged()
    }
}