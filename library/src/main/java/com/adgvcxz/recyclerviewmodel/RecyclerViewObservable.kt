package com.adgvcxz.recyclerviewmodel

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

class RecyclerViewObservable(private val recyclerView: RecyclerView): Observable<Any>() {

    private val adapter: Adapter = Adapter()

    override fun subscribeActual(observer: Observer<in Any>) {
        recyclerView.adapter = adapter
    }


    class Adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var items = arrayListOf<ItemViewModel>()

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
