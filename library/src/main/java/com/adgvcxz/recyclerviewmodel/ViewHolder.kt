package com.adgvcxz.recyclerviewmodel

import android.support.v7.widget.RecyclerView
import android.view.View
import com.adgvcxz.IAction
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel
import com.adgvcxz.bindTo

/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var viewModel: ViewModel<IModel>? = null
        set(value) {
            if (value != null && field != value) {
                field = value
//                itemView.attach()
//                        .filter { it == AttachEvent.Detach }
//                        .bindTo(value.action.dispo)
                value.model.subscribe()
            }
        }

}
