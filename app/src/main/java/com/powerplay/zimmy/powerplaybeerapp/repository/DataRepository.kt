package com.powerplay.zimmy.powerplaybeerapp.repository

import com.powerplay.zimmy.powerplaybeerapp.model.RepositoriesModel
import com.powerplay.zimmy.powerplaybeerapp.network.ApiService
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getRepositoriesList(page: Int, per_page: Int): RepositoriesModel? {
        return apiService.getPublicRepositories(page = page, per_page = per_page)
    }
}