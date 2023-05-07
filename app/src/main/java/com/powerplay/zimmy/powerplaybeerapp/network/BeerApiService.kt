package com.powerplay.zimmy.powerplaybeerapp.network

import com.powerplay.zimmy.powerplaybeerapp.model.BeerModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApiService {
    @GET(NetworkingConstants.GET_BEER)
    suspend fun getBeerList(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): BeerModel?
}