package com.essam.rippleapp.view

import com.essam.rippleapp.domain.*

class ReposListPresenter(
    private var view: ReposListContract.View?,
    private val repository: Repository,
    private var reposList: MutableList<Repo> = ArrayList()
) : ReposListContract.Presenter {

    private var pageNumber = 0
    private val pageSize = 30
    private var userQuery = ""
    private var isLoading = false
    private var areNoMoreRepos = false

    override fun searchRepos(inputQuery: String) {
        userQuery = inputQuery
        view?.showProgress(true)
        val result = getRepos(repository, userQuery, pageNumber, pageSize)
        view?.showProgress(false)
        isLoading = false
        areNoMoreRepos = false
        if (result.isSuccessful) {
            reposList = ArrayList(result.data?.items!!)
            view?.showReposList(reposList)
        } else
            view?.showError(result.error)
    }

    override fun onScrolling(
        possibleVisibleItemsOnScreen: Int,
        firstFullyVisibleItemPosition: Int,
        totalItemsInList: Int
    ) {
        if (!isLoading && !areNoMoreRepos)
            if (isValidToLoadMoreRepos(
                    possibleVisibleItemsOnScreen,
                    firstFullyVisibleItemPosition,
                    totalItemsInList
                )
            ) {
                ++pageNumber
                isLoading = true
                view?.showProgress(true)
                val result = getRepos(repository, userQuery, pageNumber, pageSize)
                isLoading = false
                view?.showProgress(false)
                if (result.isSuccessful) {
                    reposList.addAll(result.data?.items!!)
                    view?.addMoreRepos(reposList)
                } else
                    areNoMoreRepos = true
            }
    }

    override fun onDestroy() {
        view = null
    }

    override fun getState(): List<Repo> = reposList
    override fun setState(list: List<Repo>) {
        reposList = ArrayList(list)
    }


}