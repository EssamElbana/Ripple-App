package com.essam.rippleapp.domain

interface Repository {
    fun getRepos(inputQuery: String, pageNumber: Int, pageSize: Int): UseCaseResult<RepoResponse>
}