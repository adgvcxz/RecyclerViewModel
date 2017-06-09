package com.adgvcxz.recyclerviewmodel.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.adgvcxz.bindTo
import com.adgvcxz.recyclerviewmodel.RecyclerViewModel
import com.adgvcxz.recyclerviewmodel.RxRecyclerAdapter
import com.adgvcxz.recyclerviewmodel.bindTo
import com.adgvcxz.recyclerviewmodel.itemClicks
import kotlinx.android.synthetic.main.activity_main.*

/**
 * zhaowei
 * Created by zhaowei on 2017/5/10.
 */
class MainActivity : AppCompatActivity() {

    val adapter = RxRecyclerAdapter()

    val viewModel: MainActivityViewModel = MainActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.recycledViewPool

        adapter.configureCell = {
            when (it) {
                is LoadingViewModel -> LoadingView()
                is ButtonViewModel -> ButtonView()
                else -> TextView()
            }
        }

        adapter.viewModel = viewModel.listViewModel

        adapter.itemClicks()
                .subscribe { Log.e("zhaow", "$it") }


        adapter.viewModel.action.onNext(RecyclerViewModel.Action.refresh)

    }
}