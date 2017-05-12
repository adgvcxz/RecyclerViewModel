package com.adgvcxz.recyclerviewmodel.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.adgvcxz.IModel
import com.adgvcxz.recyclerviewmodel.RxRecyclerAdapter
import com.adgvcxz.recyclerviewmodel.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_example.view.*

/**
 * zhaowei
 * Created by zhaowei on 2017/5/10.
 */
class MainActivity: AppCompatActivity() {

    val adapter = RxRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val items: ArrayList<in ViewHolder<IModel>> = arrayListOf(ItemViewHolder(), ItemViewHolder(), ItemViewHolder(), ItemViewHolder(), ItemViewHolder(), ItemViewHolder())
        adapter.items = items
    }

    class ItemViewHolder: ViewHolder<ItemViewHolder.Model>(Model()) {

        class Model: IModel {
            val text1 = "abcd"
            val text2 = "abcd"
        }

        override val layoutId: Int = R.layout.item_example

        override fun bind(view: View) {
            view.text1.text = this.currentModel.text1
            view.text2.text = this.currentModel.text2
        }

    }

}