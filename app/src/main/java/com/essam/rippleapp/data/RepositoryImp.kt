package com.essam.rippleapp.data

import com.essam.rippleapp.data.cache.InMemoryCache
import com.essam.rippleapp.domain.RepoResponse
import com.essam.rippleapp.data.server_gateway.ServerGateway
import com.essam.rippleapp.domain.Repo
import com.essam.rippleapp.domain.Repository
import com.essam.rippleapp.domain.UseCaseResult

class RepositoryImp(private val serverGateway: ServerGateway, private val inMemoryCache: InMemoryCache) : Repository {
    override fun getRepos(inputQuery: String, pageNumber: Int, pageSize: Int): UseCaseResult<RepoResponse> {
        return serverGateway.getRepos(inputQuery, pageNumber, pageSize)
    }

    override fun saveRepos(key: String, list: List<Repo>) {
        inMemoryCache.save(key, list)
    }

    override fun loadRepos(key: String): List<Repo> {
        return inMemoryCache.load(key)
    }
}