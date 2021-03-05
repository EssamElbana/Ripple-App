package com.essam.rippleapp.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.essam.rippleapp.R
import com.essam.rippleapp.domain.Repo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.parceler.Parcels

private const val SAVED_STATE = "SAVED_STATE"

class MainActivity : AppCompatActivity(), ReposListContract.View {
    private val presenter: ReposListContract.Presenter by inject {
        parametersOf(this)
    }

    private val adapter = ReposAdapter()
    private val job = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = adapter

        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            presenter.setState(Parcels.unwrap(savedInstanceState.getParcelable(SAVED_STATE)))
            adapter.setDataList(presenter.getState())
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                    job.launch {
                        withContext(IO) {
                            presenter.onScrolling(
                                linearLayoutManager.childCount,
                                linearLayoutManager.findFirstCompletelyVisibleItemPosition(),
                                adapter.itemCount
                            )
                        }
                    }
            }
        })

        search_button.setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            job.launch {
                withContext(IO) {
                    presenter.searchRepos(input_query_edit_text.text.toString())
                }
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showReposList(list: List<Repo>) {
        job.launch {
            adapter.setDataList(list)
        }
    }

    override fun addMoreRepos(list: List<Repo>) {
        job.launch {
            adapter.addItemsToTheList(list)
        }
    }

    override fun showProgress(isEnabled: Boolean) {
        job.launch {
            if (isEnabled)
                progress_bar.visibility = View.VISIBLE
            else
                progress_bar.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVED_STATE, Parcels.wrap(presenter.getState()))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        job.cancel()
    }
}