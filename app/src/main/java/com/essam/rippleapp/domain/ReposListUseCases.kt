package com.essam.rippleapp.domain

fun getRepos(repository: Repository, inputQuery: String, pageNumber: Int, pageSize: Int): UseCaseResult<RepoResponse> {
    val useCaseResult = repository.getRepos(inputQuery, pageNumber, pageSize)
    useCaseResult.error = "Failed to load data"
    return useCaseResult
}

fun isValidToLoadMoreRepos(possibleVisibleItemsOnScreen :Int,
                           firstFullyVisibleItemPosition :Int,
                           totalItemsInList :Int) : Boolean {
    if (totalItemsInList > 0)
        if (possibleVisibleItemsOnScreen + firstFullyVisibleItemPosition >= totalItemsInList)
            return true
    return false
}
data class UseCaseResult<T>(
    val data: T? = null,
    val isSuccessful: Boolean,
    var error: String = ""
)