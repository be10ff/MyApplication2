package ru.abelov.myapplication

import android.arch.lifecycle.*
import android.os.Bundle

//class GeoInfoViewModel (private var count: Int = 0) : ViewModel() {
//    val changeNotifier = MutableLiveData<Int>()
//    fun increment() { changeNotifier.value = ++count}
//
//}
class GeoInfoViewModel (private var count: Int = 0) : ViewModel(), LifecycleObserver {

    companion object { const val COUNT_KEY = "CountKey" }
    val changeNotifier = MutableLiveData<Int>()
    fun increment() { changeNotifier.value = ++count}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {increment()}

    fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            count = it.getInt(COUNT_KEY)
        }
    }

    fun saveState(outState: Bundle?) {
        outState?.putInt(COUNT_KEY, count)
    }
}