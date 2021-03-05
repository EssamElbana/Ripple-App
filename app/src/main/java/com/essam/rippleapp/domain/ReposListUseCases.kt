package com.essam.rippleapp.domain

private const val SAVED_STATE = "SAVED_STATE"

fun validateInput(inputQuery: String): UseCaseResult<Any> {
    return if (inputQuery.isEmpty())
        UseCaseResult(isSuccessful = false, error = "Please input repository name")
    else
        UseCaseResult(isSuccessful = true)
}

fun getRepos(
    repository: Repository,
    inputQuery: String,
    pageNumber: Int,
    pageSize: Int
): UseCaseResult<RepoResponse> {
    val useCaseResult = repository.getRepos(inputQuery, pageNumber, pageSize)
    when {
        useCaseResult.data == null -> useCaseResult.error = "Failed to load data"
        useCaseResult.data.total_count == 0 -> useCaseResult.error = "there's no such repository!"
        else -> useCaseResult.isSuccessful = true
    }
    return useCaseResult
}

fun isValidToLoadMoreRepos(
    possibleVisibleItemsOnScreen: Int,
    firstFullyVisibleItemPosition: Int,
    totalItemsInList: Int
): Boolean {
    if (totalItemsInList > 0)
        if (possibleVisibleItemsOnScreen + firstFullyVisibleItemPosition >= totalItemsInList)
            return true
    return false
}

fun loadReposFromCache(repository: Repository): UseCaseResult<List<Repo>> {
    val result = repository.loadRepos(SAVED_STATE)
    if(result.isNotEmpty())
        return UseCaseResult(result, true)
    return UseCaseResult()
}

fun saveReposToCache(repository: Repository, list: List<Repo>) {
    repository.saveRepos(SAVED_STATE, list)
}

data class UseCaseResult<T>(
    val data: T? = null,
    var isSuccessful: Boolean = false,
    var error: String = ""
)
