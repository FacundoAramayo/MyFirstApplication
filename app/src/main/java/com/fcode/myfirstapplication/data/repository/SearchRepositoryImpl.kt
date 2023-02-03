package com.fcode.myfirstapplication.data.repository

import com.fcode.myfirstapplication.domain.Articles
import com.fcode.myfirstapplication.network.api.ApiHelper
import retrofit2.Response

object SearchRepositoryImpl: SearchRepository {
    override suspend fun fetchNews(query: String, fromDate: String, sortBy: String): Response<Articles> {
        return ApiHelper().fetchNews(query, fromDate, sortBy)
    }
}