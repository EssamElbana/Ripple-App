package com.essam.rippleapp.data

import com.essam.rippleapp.domain.RepoResponse
import com.essam.rippleapp.data.server_gateway.ServerGateway
import com.essam.rippleapp.domain.Repository
import com.essam.rippleapp.domain.UseCaseResult

class RepositoryImp(private val serverGateway: ServerGateway) : Repository {
    override fun getRepos(inputQuery: String, pageNumber: Int, pageSize: Int): UseCaseResult<RepoResponse> {
        return serverGateway.getRepos(inputQuery, pageNumber, pageSize)
    }
}