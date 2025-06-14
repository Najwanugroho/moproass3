package com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.network


import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.model.Planet
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

object PlanetApi {
    private const val BASE_URL = "https://planet-api-1.vercel.app/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val service: PlanetApiService by lazy {
        retrofit.create(PlanetApiService::class.java)
    }

data class PlanetListResponse(val success: Boolean, val data: List<Planet>)
data class PlanetDetailResponse(val success: Boolean, val data: Planet)
data class GeneralResponse(val success: Boolean, val message: String)

interface PlanetApiService {
    @GET("api/planets")
    suspend fun getAllPlanets(): PlanetListResponse

    @Multipart
    @POST("api/planets")
    suspend fun createPlanet(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part
    ): PlanetDetailResponse

    @PUT("api/planets/{id}")
    suspend fun updatePlanet(
        @Path("id") planetId: Int,
        @Body planetUpdate: Map<String, String>
    ): PlanetDetailResponse

    @DELETE("api/planets/{id}")
    suspend fun deletePlanet(@Path("id") planetId: Int): GeneralResponse
}

}

enum class ApiStatus { LOADING, SUCCESS, FAILED }