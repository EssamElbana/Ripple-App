package com.essam.rippleapp.view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.essam.rippleapp.R
import com.essam.rippleapp.data.RepositoryImp
import com.essam.rippleapp.data.server_gateway.ServerGatewayImp
import com.essam.rippleapp.domain.Repo
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.parceler.Parcels
private const val SAVED_STATE = "SAVED_STATE"

class MainActivity : AppCompatActivity(), ReposListContract.View {
    private val presenter: ReposListContract.Presenter by lazy {
        ReposListPresenter(this, RepositoryImp(ServerGatewayImp()))
    }
    private val adapter = ReposAdapter()
    private val job = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputQueryEditText = findViewById<TextView>(R.id.input_query_edit_text)
        val searchButton = findViewById<Button>(R.id.search_button)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            presenter.setState(Parcels.unwrap(savedInstanceState.getParcelable(SAVED_STATE)))
            adapter.setDataList(presenter.getState())
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    job.launch {
                        withContext(IO) {
                            presenter.onScrolling(
                                linearLayoutManager.childCount,
                                linearLayoutManager.findFirstCompletelyVisibleItemPosition(),
                                recyclerView.adapter?.itemCount ?: 0
                            )
                        }
                    }
                }
            }
        })

        searchButton.setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            job.launch {
                withContext(IO) {
                    presenter.searchRepos(inputQueryEditText.text.toString())
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
            val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
            if (isEnabled)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
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