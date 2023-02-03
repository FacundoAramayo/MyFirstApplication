package com.fcode.myfirstapplication.network.api

import com.fcode.myfirstapplication.API_KEY
import com.fcode.myfirstapplication.domain.Articles
import com.fcode.myfirstapplication.network.RetrofitService
import retrofit2.Response

class ApiHelper {

    lateinit var apiService: ApiService

    init {
        initAPI()
    }

    private fun initAPI() {
        apiService = RetrofitService.createService(ApiService::class.java)
    }

    suspend fun fetchNews(query: String, fromDate: String, sortBy: String): Response<Articles> {
        return apiService.getNews(query, fromDate, sortBy, API_KEY, 25)
    }
}