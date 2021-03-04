package com.essam.rippleapp.data.server_gateway

import com.essam.rippleapp.domain.UseCaseResult
import com.essam.rippleapp.domain.RepoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerGatewayImp() : ServerGateway {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubService = retrofit.create(GitHubService::class.java)
    override fun getRepos(
        query: String,
        pageNumber: Int,
        pageSize: Int
    ): UseCaseResult<RepoResponse> {
        val call = service.listRepos(query, pageNumber, pageSize)
        val result = call.execute()
        return if(result.isSuccessful && result.body() != null)
            UseCaseResult(result.body(), true)
        else
            UseCaseResult(isSuccessful = false, error = result.message())
    }


}