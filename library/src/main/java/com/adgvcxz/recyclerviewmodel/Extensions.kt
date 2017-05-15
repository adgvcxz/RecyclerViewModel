package com.adgvcxz.recyclerviewmodel

import android.view.View
import io.reactivex.Observable

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
