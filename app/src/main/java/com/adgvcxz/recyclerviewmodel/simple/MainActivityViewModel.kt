package com.adgvcxz.recyclerviewmodel.simple

import android.util.Log
import android.view.View
import com.adgvcxz.*
import com.adgvcxz.recyclerviewmodel.IView
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
        var items: List<ViewModel<out IModel>> = (0 until 1).map {
//            if (it % 2 == 0) {
//                ItemViewModel()
//            } else {
                ButtonViewModel()
//            }
        }
    }

    enum class Mutation: IMutation {
        add100Items
    }

    override fun transform(mutation: Observable<IMutation>): Observable<IMutation> {
        return mutation.mergeWith(event.flatMap {
            Observable.interval(1, TimeUnit.SECONDS).map { Mutation.add100Items }.take(10)
        })
    }

    override fun scan(model: Model, mutation: IMutation): Model {
        when(mutation) {
            Mutation.add100Items -> {
                val items = (0 until 1).map {
                    //                if (it % 2 == 0) {
//                    ItemViewModel()
//                } else {
                    ButtonViewModel()
//                }
                }
                model.items = items + model.items
            }
        }
        return model
    }
}

class ItemViewModel : ViewModel<ItemViewModel.Model>(Model()) {

    class Model : IModel {
        val text1 = "abcd"
        val text2 = "abcd"
    }
}


class TextView: IView<ItemViewModel> {

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

    enum class Action: IAction {
        button1DidClick,
        button2DidClick
    }

    enum class Mutation(var value: Any): IMutation {
        updateText2(value = String),
    }

    override fun mutate(action: IAction): Observable<IMutation> {
        when(action) {
            Action.button1DidClick -> event.onNext(Event())
            Action.button2DidClick -> return Observable.just(Mutation.updateText2.also { it.value = "${System.currentTimeMillis()}" })
        }
        return super.mutate(action)
    }

    override fun scan(model: Model, mutation: IMutation): Model {
        when(mutation as Mutation) {
            Mutation.updateText2 -> {
                model.text2 = (mutation.value as String)
                Log.e("zhaow", model.text2)
            }
        }
        return model
    }
}

class ButtonView: IView<ButtonViewModel> {

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