package com.adgvcxz.recyclerviewmodel.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.adgvcxz.recyclerviewmodel.RxRecyclerAdapter
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
        viewModel.model.map { it.items }
                .subscribe { adapter.items = it }
    }
}