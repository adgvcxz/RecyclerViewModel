package com.adgvcxz.recyclerviewmodel.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.adgvcxz.recyclerviewmodel.RxRecyclerAdapter
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

        adapter.configureCell = {
            if (it % 2 == 0) {
                TextView()
            } else {
                ButtonView()
            }
        }

        viewModel.model.map { it.items }
                .subscribe { adapter.items = it }

        adapter.itemClicks()
                .subscribe { Log.e("zhaow", "$it") }
    }
}