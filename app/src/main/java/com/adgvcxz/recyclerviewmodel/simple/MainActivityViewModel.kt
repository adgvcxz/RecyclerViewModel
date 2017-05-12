package com.adgvcxz.recyclerviewmodel.simple

import android.view.View
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel
import com.adgvcxz.recyclerviewmodel.ViewHolder
import kotlinx.android.synthetic.main.item_example.view.*
import kotlinx.android.synthetic.main.item_example_1.view.*


/**
 * zhaowei
 * Created by zhaowei on 2017/5/12.
 */

class MainActivityViewModel : ViewModel<MainActivityViewModel.Model>(Model()) {

    class Model : IModel {
        val items = arrayListOf(ItemViewHolder(), ButtonViewHolder(), ItemViewHolder(), ButtonViewHolder(), ItemViewHolder(), ButtonViewHolder())
    }

}

class ItemViewHolder : ViewHolder<ItemViewHolder.Model>(Model()) {

    class Model : IModel {
        val text1 = "abcd"
        val text2 = "abcd"
    }

    override val layoutId: Int = R.layout.item_example

    override fun bind(view: View) {
        view.text1.text = this.currentModel.text1
        view.text2.text = this.currentModel.text2
    }

}

class ButtonViewHolder : ViewHolder<ButtonViewHolder.Model>(Model()) {

    class Model : IModel {
        val text1 = "abcd"
        val text2 = "abcd"
    }

    override val layoutId: Int = R.layout.item_example_1

    override fun bind(view: View) {
        view.button1.text = this.currentModel.text1
        view.button2.text = this.currentModel.text2
    }

}