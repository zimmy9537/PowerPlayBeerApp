package com.powerplay.zimmy.powerplaybeerapp.usecase

import android.util.Log
import com.powerplay.zimmy.powerplaybeerapp.model.RepositoriesModel
import com.powerplay.zimmy.powerplaybeerapp.model.RepositoriesModelItem
import com.powerplay.zimmy.powerplaybeerapp.network.ResultData
import com.powerplay.zimmy.powerplaybeerapp.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend fun getRepositoriesList(page: Int, per_page: Int): Flow<ResultData<RepositoriesModel>> {
        return flow {
            emit(ResultData.Loading)

            val repositoriesModel = dataRepository.getRepositoriesList(page, per_page)
            val beerList = repositoriesModel as ArrayList<RepositoriesModelItem>

            val resultData = if (beerList.isEmpty()) {
                ResultData.Failed()
            } else {
                ResultData.Success(repositoriesModel)
            }

            emit(resultData)
        }.catch {
            Log.d("theBeerApp","theBeerApp $it")
            emit(ResultData.Failed())
        }
    }
}