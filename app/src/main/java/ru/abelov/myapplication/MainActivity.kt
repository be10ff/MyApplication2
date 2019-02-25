package ru.abelov.myapplication

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val uiSccope = CoroutineScope(Dispatchers.Main + Job())

    private val viewModel: GeoInfoViewModel by lazy {
        ViewModelProviders.of(this).get(GeoInfoViewModel::class.java)
    }

    private val changeObserver = Observer<Int> { value ->
        value?.let {
            incrementCount(value)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.restoreState(savedInstanceState)
        viewModel.changeNotifier.observe(this, changeObserver)
        lifecycle.addObserver(viewModel)
        svCompass.setOnClickListener { viewModel.increment() }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)
    }

    override fun onResume() {
        super.onResume()
        uiSccope.launch {
            loader.visibility = View.VISIBLE
            val loading = GlobalScope.launch { load("path") }
            loading.join()
            loader.visibility = View.GONE
        }
//        runBlocking {
//            loader.visibility = View.VISIBLE
//            val loading = GlobalScope.launch { load("path") }
//            loading.join()
//            loader.visibility = View.GONE
//        }
    }

    suspend fun load(path: String) {
        delay(3000)
    }

    private fun incrementCount(value: Int) {
        svCompass.set(value)
    }
}
