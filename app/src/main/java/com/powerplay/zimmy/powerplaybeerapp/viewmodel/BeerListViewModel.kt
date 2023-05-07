package com.powerplay.zimmy.powerplaybeerapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powerplay.zimmy.powerplaybeerapp.model.BeerModel
import com.powerplay.zimmy.powerplaybeerapp.model.BeerModelItem
import com.powerplay.zimmy.powerplaybeerapp.network.ResultData
import com.powerplay.zimmy.powerplaybeerapp.usecase.DataUseCase
import com.powerplay.zimmy.powerplaybeerapp.util.ListNotifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(private val useCase: DataUseCase) : ViewModel() {

    private val _beerModelLiveData: MutableLiveData<ResultData<BeerModel>> =
        MutableLiveData()
    val beerModelLiveData: LiveData<ResultData<BeerModel>>
        get() = _beerModelLiveData

    private val _beerList = arrayListOf<BeerModelItem>()
    val beerList: List<BeerModelItem> get() = _beerList

    val isLastPage = AtomicBoolean(false)
    val isLoading = AtomicBoolean(false)

    var pageCount = 1
        private set
    private var pageSize = 20
        set(value) {
            pageCount = 1
            isLastPage.set(false)
            field = value
        }

    private val _beerListNotifier = MutableLiveData<ListNotifier>()
    val beerListNotifier: LiveData<ListNotifier> get() = _beerListNotifier

    private fun getBeerList(page: Int, per_page: Int) {
        if (pageCount == 1) {
            _beerList.clear()
            _beerListNotifier.postValue(
                ListNotifier.NotifyDataChanged
            )
        }
        viewModelScope.launch {
            useCase.getBeerList(page, per_page)
                .onEach { result ->
                    when (result) {
                        is ResultData.Success -> {

                            val response = result.data

                            response.let {
                                _beerList.addAll(response as ArrayList<BeerModelItem>)
                                _beerListNotifier.postValue(
                                    ListNotifier.NotifyDataChanged
                                )
                            }

                            if (result.data?.isEmpty() == true) {
                                isLastPage.set(true)
                                _beerList.clear()
                            }

                            isLoading.set(false)
                        }

                        is ResultData.Failed -> {
                            isLoading.set(false)
                        }

                        else -> {}
                    }
                    _beerModelLiveData.postValue(result)
                }.collect()
        }
    }

    fun incrementPageCount() {
        pageCount += 1
    }

    fun fetchData() {
        getBeerList(pageCount, pageSize)
    }

    fun resetLists() {
        pageCount = 1
        isLoading.set(false)
        isLastPage.set(false)
    }

}