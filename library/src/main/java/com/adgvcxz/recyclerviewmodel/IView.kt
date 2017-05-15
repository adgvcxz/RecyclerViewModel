package com.adgvcxz.recyclerviewmodel

import android.view.View
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel

/**
 * zhaowei
 * Created by zhaowei on 2017/5/11.
 */
interface IView<in V: ViewModel<out IModel>> {


    fun initView(view: View) {

    }

    fun bind(view: View, viewModel: V)
}