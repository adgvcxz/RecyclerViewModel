package com.adgvcxz.recyclerviewmodel

import com.adgvcxz.IModel
import com.adgvcxz.ViewModel

/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */

abstract class ViewHolder<M : IModel>(initModel: M) : ViewModel<M>(initModel) {
    abstract val layoutId: Int
}
