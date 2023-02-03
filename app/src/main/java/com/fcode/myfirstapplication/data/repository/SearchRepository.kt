package com.fcode.myfirstapplication.data.repository

import com.fcode.myfirstapplication.domain.Articles
import retrofit2.Response

interface SearchRepository {
    suspend fun fetchNews(query: String, fromDate: String, sortBy: String): Response<Articles>
}