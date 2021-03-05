package com.essam.rippleapp.presentation

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

    override fun searchRepos(inputQuery: String) {
        userQuery = inputQuery
        val validationResult = validateInput(inputQuery)
        if (!validationResult.isSuccessful)
            view?.showError(validationResult.error)
        else {
            view?.showProgress(true)
            val result = getRepos(repository, userQuery, pageNumber, pageSize)
            view?.showProgress(false)
            isLoading = false
            if (result.isSuccessful) {
                reposList = ArrayList(result.data?.items!!)
                view?.showReposList(reposList)
            } else
                view?.showError(result.error)
        }
    }

    override fun onScrolling(
        possibleVisibleItemsOnScreen: Int,
        firstFullyVisibleItemPosition: Int,
        totalItemsInList: Int
    ) {
        if (!isLoading)
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
                }
            }
    }

    override fun onDestroy() {
        view = null
    }

    override fun setState(list: List<Repo>) {
        reposList = ArrayList(list)
    }

    override fun getState(): List<Repo> {
        return reposList
    }

}