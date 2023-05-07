package com.powerplay.zimmy.powerplaybeerapp.usecase

import com.powerplay.zimmy.powerplaybeerapp.model.BeerModel
import com.powerplay.zimmy.powerplaybeerapp.model.BeerModelItem
import com.powerplay.zimmy.powerplaybeerapp.network.ResultData
import com.powerplay.zimmy.powerplaybeerapp.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    suspend fun getBeerList(page: Int, per_page: Int): Flow<ResultData<BeerModel>> {
        return flow {
            emit(ResultData.Loading)

            val beerModel = beerRepository.getBeerList(page, per_page)
            val beerList = beerModel as ArrayList<BeerModelItem>

            val resultData = if (beerList.isEmpty()) {
                ResultData.Failed()
            } else {
                ResultData.Success(beerModel)
            }

            emit(resultData)
        }.catch {
            emit(ResultData.Failed())
        }
    }
}