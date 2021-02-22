package com.work.homework2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.work.homework2.data.BeerModelsItem
import com.work.homework2.service.ApiService
import com.work.homework2.service.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

open class BeerViewModel : ViewModel() {
    // 기본 URL
    private val BASE_URL = "https://api.punkapi.com/v2/"
    // http call 만들기
    private val apiService: ApiService = RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
    private val _beerList = MutableLiveData<List<BeerModelsItem>>()
    val beerList: LiveData<List<BeerModelsItem>> = _beerList

    private val _beerContext = MutableLiveData<List<BeerModelsItem>>()
    private val beerContext: LiveData<List<BeerModelsItem>> = _beerContext

    open fun setBeerData(index: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getBeers(index).awaitResponse()
            delay(2000L)
            if (response.isSuccessful) {
                _beerList.postValue(response.body())
            }
        }
    }

    open fun getBeerData(): LiveData<List<BeerModelsItem>> {
        return beerList
    }

    open fun setBeerContext(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getContext(id).awaitResponse()
            delay(1000L)
            if (response.isSuccessful) {
                _beerContext.postValue(response.body())
            }
        }
    }

    open fun getBeerContext(): LiveData<List<BeerModelsItem>> {
        return beerContext
    }
}