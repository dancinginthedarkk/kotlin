package com.example.secondapp.datalayer

import com.example.secondapp.objects.Item
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {
    @GET("/api/cats?skip=0&limit=30")
    fun loadCats() : Single<List<Item>>
}

