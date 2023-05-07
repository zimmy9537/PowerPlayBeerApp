package com.powerplay.zimmy.powerplaybeerapp.network

import com.powerplay.zimmy.powerplaybeerapp.model.RepositoriesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(NetworkingConstants.GET_BEER)
    suspend fun getPublicRepositories(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): RepositoriesModel?
}