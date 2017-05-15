package com.adgvcxz.recyclerviewmodel.simple

import android.view.View
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel
import com.adgvcxz.bindTo
import com.adgvcxz.recyclerviewmodel.IView
import com.adgvcxz.recyclerviewmodel.ViewHolder
import com.jakewharton.rxbinding2.widget.text
import kotlinx.android.synthetic.main.item_example.view.*
import kotlinx.android.synthetic.main.item_example_1.view.*


/**
 * zhaowei
 * Created by zhaowei on 2017/5/12.
 */

class MainActivityViewModel : ViewModel<MainActivityViewModel.Model>(Model()) {

    class Model : IModel {
        val items: List<ViewHolder<out IModel>> = (1..100).map {
            if (it % 2 == 0) {
                ItemViewHolder()
            } else {
                ButtonViewHolder()
            }
        }
    }
}

class ItemViewHolder : ViewHolder<ItemViewHolder.Model>(Model()) {

    class Model : IModel {
        val text1 = "abcd"
        val text2 = "abcd"
    }

    override val layoutId: Int = R.layout.item_example


}

class ButtonViewHolder : ViewHolder<ButtonViewHolder.Model>(Model()) {

    class Model : IModel {
        val text1 = "abcd"
        val text2 = "abcd"
    }

    override val layoutId: Int = R.layout.item_example_1

}

class TextView: IView<ItemViewHolder> {

    override fun bind(view: View, viewModel: ItemViewHolder) {
        viewModel.model.map { it.text1 }
                .subscribe(view.text1.text())

        viewModel.model.map { it.text2 }
                .subscribe(view.text2.text())

    }
}

class ButtonView: IView<ButtonViewHolder> {

    override fun bind(view: View, viewModel: ButtonViewHolder) {
        viewModel.model.map { it.text1 }
                .subscribe(view.button1.text())

        viewModel.model.map { it.text2 }
                .subscribe(view.button2.text())
    }
}