package com.essam.rippleapp.presentation

import com.essam.rippleapp.domain.Repo

interface ReposListContract {

    interface View {
        fun showError(message: String)
        fun showReposList(list: List<Repo>)
        fun addMoreRepos(list: List<Repo>)
        fun showProgress(isEnabled: Boolean)
    }

    interface Presenter {
        fun onViewCreated()
        fun searchRepos(inputQuery: String)
        fun onScrolling(
            possibleVisibleItemsOnScreen: Int,
            firstFullyVisibleItemPosition: Int,
            totalItemsInList: Int
        )
        fun onDestroy()
    }

}