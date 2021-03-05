package com.essam.rippleapp.domain

interface Repository {
    fun getRepos(inputQuery: String, pageNumber: Int, pageSize: Int): UseCaseResult<RepoResponse>

    fun saveRepos(key: String, list: List<Repo>)

    fun loadRepos(key: String) : List<Repo>
}