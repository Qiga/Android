package com.github.splashgallery.api

import com.github.splashgallery.model.PhotoData
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {

    @GET("photos/random/?client_id=V7Wn1Pn53SVnjKrfCrS0X9fwPR8ebh0FmHfWIMnRg48&count=15")
    suspend fun getItemWithName(@Query("query") query: String?) : ArrayList<PhotoData>

}