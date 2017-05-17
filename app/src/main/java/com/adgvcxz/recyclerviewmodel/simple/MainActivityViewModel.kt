package com.adgvcxz.recyclerviewmodel.simple

import android.util.Log
import android.view.View
import com.adgvcxz.*
import com.adgvcxz.recyclerviewmodel.IView
import com.adgvcxz.recyclerviewmodel.RecyclerViewModel
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.text
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.item_example.view.*
import kotlinx.android.synthetic.main.item_example_1.view.*
import java.util.concurrent.TimeUnit


/**
 * zhaowei
 * Created by zhaowei on 2017/5/12.
 */

val event: Subject<Event> = PublishSubject.create<Event>().toSerialized()

class Event

class MainActivityViewModel : ViewModel<MainActivityViewModel.Model>(Model()) {

    class Model : IModel {
//        var isLoading: Boolean = false
//        var items: List<ViewModel<out IModel>> = (0 until 10).map {
//            if (it % 2 == 0) {
//                ItemViewModel()
//            } else {
//                ButtonViewModel()
//            }
//        }
//
//        init {
//            items += LoadingViewModel()
//        }
    }

    val listViewModel: RecyclerViewModel = ListViewModel()

    enum class Action : IAction {
        loadMore
    }

    enum class Mutation : IMutation {
        insertItemToTop,
        insertItemToBottom,
        setLoadingTrue,
        setLoadingFalse
    }

    override fun mutate(action: IAction): Observable<IMutation> {
//        when(action) {
//            Action.loadMore -> {
//                if (currentModel.isLoading) { return super.mutate(action) }
//                val loadMore = Observable.timer(3, TimeUnit.SECONDS).map { Mutation.insertItemToBottom }.map { it }
//                return Observable.concat(Observable.just(Mutation.setLoadingTrue), loadMore, Observable.just(Mutation.setLoadingFalse))
//            }
//        }
        return super.mutate(action)
    }

    override fun transform(mutation: Observable<IMutation>): Observable<IMutation> {
        return mutation.mergeWith(event.flatMap {
            Observable.interval(1, TimeUnit.SECONDS).map { Mutation.insertItemToTop }.take(10)
        })
    }

    override fun scan(model: Model, mutation: IMutation): Model {
//        when (mutation) {
//            Mutation.setLoadingTrue ->  {
//                Log.e("zhaow", "setLoadingTrue")
//                model.isLoading = true
//            }
//            Mutation.setLoadingFalse ->  {
//                Log.e("zhaow", "setLoadingFalse")
//                model.isLoading = false
//            }
//            Mutation.insertItemToTop -> {
//                val items = (0 until 1).map {
//                    if (it % 2 == 0) {
//                        ItemViewModel()
//                    } else {
//                        ButtonViewModel()
//                    }
//                }
//                model.items = items + model.items
//            }
//            Mutation.insertItemToBottom -> {
//                Log.e("zhaow", "insertItemToBottom")
//                val items = (0 until 2).map {
//                    if (it % 2 == 0) {
//                        ItemViewModel()
//                    } else {
//                        ButtonViewModel()
//                    }
//                }
//                model.items = model.items.subList(0, model.items.size - 1) + items + model.items.subList(model.items.size - 1, model.items.size)
//            }
//        }
        return model
    }

    inner class ListViewModel : RecyclerViewModel() {

//        init {
//            this.action.onNext(Action.refresh)
//        }


        override fun refresh(): Observable<List<ViewModel<out IModel>>> {
            val items: List<ViewModel<out IModel>> = (0 until 10).map {
                if (it % 2 == 0) {
                    ItemViewModel()
                } else {
                    ButtonViewModel()
                }
            }
            return Observable.just(items).delay(3, TimeUnit.SECONDS)
        }
    }
}

class ItemViewModel : ViewModel<ItemViewModel.Model>(Model()) {

    class Model : IModel {
        val text1 = "abcd"
        val text2 = "abcd"
    }
}


class TextView : IView<ItemViewModel> {

    override val layoutId: Int = R.layout.item_example

    override fun bind(view: View, viewModel: ItemViewModel) {

        viewModel.model.map { it.text1 }
                .subscribe(view.text1.text())

        viewModel.model.map { it.text2 }
                .subscribe(view.text2.text())
    }

}

class ButtonViewModel : ViewModel<ButtonViewModel.Model>(Model()) {

    class Model : IModel {
        val text1 = "abcd"
        var text2 = "abcd"
    }

    enum class Action : IAction {
        button1DidClick,
        button2DidClick
    }

    enum class Mutation(var value: Any) : IMutation {
        updateText2(value = String),
    }

    override fun mutate(action: IAction): Observable<IMutation> {
        when (action) {
            Action.button1DidClick -> event.onNext(Event())
            Action.button2DidClick -> return Observable.just(Mutation.updateText2.also { it.value = "${System.currentTimeMillis()}" })
        }
        return super.mutate(action)
    }

    override fun scan(model: Model, mutation: IMutation): Model {
        when (mutation as Mutation) {
            Mutation.updateText2 -> {
                model.text2 = (mutation.value as String)
            }
        }
        return model
    }
}

class ButtonView : IView<ButtonViewModel> {

    override val layoutId: Int = R.layout.item_example_1

    override fun bind(view: View, viewModel: ButtonViewModel) {
        viewModel.model.map { it.text1 }
                .subscribe(view.button1.text())

        viewModel.model.map { it.text2 }
                .subscribe(view.button2.text())

        view.button1.clicks()
                .map { ButtonViewModel.Action.button1DidClick }
                .bindTo(viewModel.action)

        view.button2.clicks()
                .map { ButtonViewModel.Action.button2DidClick }
                .bindTo(viewModel.action)
    }
}

class LoadingViewModel : ViewModel<LoadingViewModel.Model>(Model()) {

    class Model : IModel
}

class LoadingView : IView<LoadingViewModel> {

    override val layoutId: Int = R.layout.item_loading

    override fun bind(view: View, viewModel: LoadingViewModel) {
    }

}