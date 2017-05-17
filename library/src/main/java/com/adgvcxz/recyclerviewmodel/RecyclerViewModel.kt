package com.adgvcxz.recyclerviewmodel

import com.adgvcxz.IAction
import com.adgvcxz.IModel
import com.adgvcxz.IMutation
import com.adgvcxz.ViewModel
import io.reactivex.Observable

/**
 * zhaowei
 * Created by zhaowei on 2017/5/17.
 */

open class RecyclerViewModel : ViewModel<RecyclerViewModel.Model>(Model()) {

    class Model : IModel {
        var loadingViewModel: LoadingViewModel? = null
        var items: List<ViewModel<out IModel>> = arrayListOf()
    }

    enum class Action : IAction {
        refresh,
        loadMore
    }

    data class InsertToTopMutation(val value: List<ViewModel<out IModel>>): IMutation
    data class InsertToBottomMutation(val value: List<ViewModel<out IModel>>): IMutation

    enum class Mutation : IMutation {
        refresh,
        setLoadingTrue,
        setLoadingFalse
    }

    override fun mutate(action: IAction): Observable<IMutation> {
        when (action) {
            Action.refresh -> {
                val start = Observable.just(Mutation.setLoadingTrue)
                val end = Observable.just(Mutation.setLoadingFalse)
                return Observable.concat(start,
                        refresh().map { InsertToTopMutation(it) },
                        end)
            }
        }
        return super.mutate(action)
    }

    override fun scan(model: Model, mutation: IMutation): Model {
        when (mutation) {
            is InsertToTopMutation -> {
                model.items = model.items + mutation.value
            }
            is InsertToBottomMutation -> {
                model.items = mutation.value
            }
        }
        return model
    }

    open fun refresh(): Observable<List<ViewModel<out IModel>>> {
        return Observable.empty()
    }


    val count: Int
        get() {
            if (this.currentModel.items.isEmpty() || this.currentModel.loadingViewModel == null) {
                return this.currentModel.items.size
            } else {
                return this.currentModel.items.size + 1
            }
        }

}
