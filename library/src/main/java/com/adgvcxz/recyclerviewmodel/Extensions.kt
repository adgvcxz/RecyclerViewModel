package com.adgvcxz.recyclerviewmodel

import android.support.v7.util.DiffUtil
import android.view.View
import com.adgvcxz.IModel
import com.adgvcxz.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * zhaowei
 * Created by zhaowei on 2017/5/12.
 */

fun View.attach(): Observable<AttachEvent> {
    return ViewAttachesObservable(this)
}

fun RxRecyclerAdapter.itemClicks(): Observable<Int> {
    return ItemClickObservable(this)
}



fun Observable<List<ViewModel<out IModel>>>.bindTo(adapter: RxRecyclerAdapter, anim: Boolean = false): Disposable {
    return this.observeOn(Schedulers.computation())
            .scan(Pair<List<ViewModel<out IModel>>,
                    DiffUtil.DiffResult?>(arrayListOf<ViewModel<out IModel>>(), null)) { (first), list ->
                if (anim) {
                    val diff = ItemDiffCallback(first, list)
                    val result = DiffUtil.calculateDiff(diff, true)
                    Pair(list, result)
                } else {
                    Pair(list, null)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(adapter)
}