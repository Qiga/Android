package com.github.dalle.api

import com.github.dalle.data.UserImg
import retrofit2.http.GET

interface UnsplashService {

    @GET("photos/random/?client_id=V7Wn1Pn53SVnjKrfCrS0X9fwPR8ebh0FmHfWIMnRg48&count=5")
    suspend fun getLiveUserImg(): List<UserImg>
}