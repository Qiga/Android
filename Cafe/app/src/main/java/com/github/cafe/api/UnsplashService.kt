package com.github.cafe.api


import com.github.cafe.data.PhotoData
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {
    @GET("photos/random/?client_id=V7Wn1Pn53SVnjKrfCrS0X9fwPR8ebh0FmHfWIMnRg48&count=30")
    suspend fun getRandomPhotos(@Query("query") query: String?): List<PhotoData>
}

