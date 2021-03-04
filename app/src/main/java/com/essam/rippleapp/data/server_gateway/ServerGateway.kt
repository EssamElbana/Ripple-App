package com.essam.rippleapp.data.server_gateway

import com.essam.rippleapp.domain.UseCaseResult
import com.essam.rippleapp.domain.RepoResponse

interface ServerGateway {

    fun getRepos(query: String, pageNumber: Int, pageSize: Int): UseCaseResult<RepoResponse>
}