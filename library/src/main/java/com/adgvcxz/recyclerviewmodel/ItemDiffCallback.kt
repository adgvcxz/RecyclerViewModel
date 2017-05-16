package com.adgvcxz.recyclerviewmodel

import android.support.v7.util.DiffUtil
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel

/**
 * zhaowei
 * Created by zhaowei on 2017/5/16.
 */

class ItemDiffCallback(private val oldItems: List<ViewModel<out IModel>>,
                       private val newItems: List<ViewModel<out IModel>>): DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].currentModel == newItems[newItemPosition].currentModel
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

}
