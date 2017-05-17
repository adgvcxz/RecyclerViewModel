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

class LoadingViewModel: ViewModel<LoadingViewModel.Model>(Model()) {

    enum class State {
        success,
        failure,
        loading
    }

    class Model: IModel {
        var state: State = State.success
    }

    enum class Action: IAction {
        startLoading,
        stopLoading
    }

    enum class Mutation: IMutation {
        loadSuccess,
        loadFailure,
        setLoading
    }

    override fun mutate(action: IAction): Observable<IMutation> {
//        when(action) {
//            Action.startLoading -> return Observable.just(Mutation.startLoading)
//            Action.stopLoading -> return Observable.just(Mutation.stopLoading)
//        }
        return super.mutate(action)
    }

    override fun scan(model: Model, mutation: IMutation): Model {
        when(mutation) {
            Mutation.loadSuccess -> model.state = State.success
            Mutation.loadFailure -> model.state = State.failure
            Mutation.setLoading -> model.state = State.loading
        }
        return model
    }
}
