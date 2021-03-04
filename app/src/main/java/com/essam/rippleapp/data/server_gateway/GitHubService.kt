package com.essam.rippleapp.data.server_gateway

import com.essam.rippleapp.domain.RepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("/search/repositories?")
    fun listRepos(
        @Query("q") searchParameter: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<RepoResponse?>
}