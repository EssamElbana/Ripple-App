package com.essam.rippleapp.view

import com.essam.rippleapp.domain.Repo

interface ReposListContract {

    interface View {
        fun showError(message: String)
        fun showReposList(list: List<Repo>)
        fun addMoreRepos(list: List<Repo>)
        fun showProgress(isEnabled: Boolean)
    }

    interface Presenter {
        fun searchRepos(inputQuery: String)
        fun onScrolling(
            possibleVisibleItemsOnScreen: Int,
            firstFullyVisibleItemPosition: Int,
            totalItemsInList: Int
        )

        fun getState(): List<Repo>
        fun setState(list: List<Repo>)
        fun onDestroy()
    }

}