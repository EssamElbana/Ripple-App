package com.essam.rippleapp.data.server_gateway

import com.essam.rippleapp.domain.Repo
import com.essam.rippleapp.domain.RepoResponse
import com.essam.rippleapp.domain.UseCaseResult

class ServerGatewayFakeImp : ServerGateway {
    override fun getRepos(
        query: String,
        pageNumber: Int,
        pageSize: Int
    ): UseCaseResult<RepoResponse> {

        // error case
        if(pageSize * pageNumber >= 100)
            return UseCaseResult(isSuccessful = false, error = "failure")

        // normal case
        val repoList = ArrayList<Repo>()
        repeat(pageSize) {
            repoList.add(Repo(it.toLong(), "Github Repo $it", "Github Repo $it description", null))
        }

        return UseCaseResult(RepoResponse(pageNumber * pageSize, repoList), true)
    }
}