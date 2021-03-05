package com.essam.rippleapp.data

import com.essam.rippleapp.data.server_gateway.GitHubService
import com.essam.rippleapp.data.server_gateway.ServerGateway
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
        return try {
            val result = call.execute()
            return if (result.isSuccessful && result.body() != null)
                UseCaseResult(result.body())
            else
                UseCaseResult(error = result.message())

        } catch (ex: Exception) {
            ex.printStackTrace()
            UseCaseResult(error = ex.message.toString())
        }
    }


}