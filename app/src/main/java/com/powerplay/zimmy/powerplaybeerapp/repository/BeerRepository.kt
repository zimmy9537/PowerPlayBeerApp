package com.powerplay.zimmy.powerplaybeerapp.repository

import com.powerplay.zimmy.powerplaybeerapp.model.BeerModel
import com.powerplay.zimmy.powerplaybeerapp.network.BeerApiService
import javax.inject.Inject

class BeerRepository @Inject constructor(private val beerApiService: BeerApiService) {
    suspend fun getBeerList(page: Int, per_page: Int): BeerModel? {
        return beerApiService.getBeerList(page = page, per_page = per_page)
    }
}