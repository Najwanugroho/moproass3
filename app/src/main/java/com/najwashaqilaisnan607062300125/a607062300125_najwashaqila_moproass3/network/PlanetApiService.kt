package com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.network

import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.model.Planet
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://684ae2bf165d05c5d35ab603.mockapi.io"

private  val moshi=Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit =Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PlanetApiService  {
    @GET("/api/v1/planets")
    suspend fun getPlanet(): List<Planet>
}

object PlanetApi{
    val service:PlanetApiService by lazy {
        retrofit.create(PlanetApiService::class.java)
    }
}
enum class ApiStatus{LOADING,SUCCESS,FAILED}
