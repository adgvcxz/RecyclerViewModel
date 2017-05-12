package com.adgvcxz.recyclerviewmodel

import android.view.View
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel

/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

abstract class ViewHolder<M: IModel>(initModel: M): ViewModel<M>(initModel), IView {

    open fun bind(view: View) {
    }

}
