package com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.network

import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.model.Planet
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://684ae2bf165d05c5d35ab603.mockapi.io/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface PlanetApiService {


    @GET("api/v1/planets")
    suspend fun getAllPlanets(): List<Planet>


    @GET("api/v1/planets/{id}")
    suspend fun getPlanetById(
        @Path("id") id: String
    ): Planet


    @GET("api/v1/planets")
    suspend fun getPlanetsByType(
        @Query("type") type: String
    ): List<Planet>


    @GET("api/v1/planets")
    suspend fun getPlanetsPaged(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Planet>
}


object PlanetApi {
    val service: PlanetApiService by lazy {
        retrofit.create(PlanetApiService::class.java)
    }
}


enum class ApiStatus {
    LOADING,
    SUCCESS,
    FAILED
}
