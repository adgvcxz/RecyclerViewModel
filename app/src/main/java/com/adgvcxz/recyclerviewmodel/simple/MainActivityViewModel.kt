package com.adgvcxz.recyclerviewmodel.simple

import android.view.View
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel
import com.adgvcxz.recyclerviewmodel.ViewHolder
import kotlinx.android.synthetic.main.item_example.view.*


/**
 * zhaowei
 * Created by zhaowei on 2017/5/12.
 */

class MainActivityViewModel: ViewModel<MainActivityViewModel.Model>(Model()) {

    class Model: IModel {
        val items: ArrayList<in ViewHolder<IModel>> = arrayListOf(ItemViewHolder(), ItemViewHolder(), ItemViewHolder(), ItemViewHolder(), ItemViewHolder(), ItemViewHolder())
    }

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