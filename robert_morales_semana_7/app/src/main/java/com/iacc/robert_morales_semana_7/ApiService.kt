package com.iacc.robert_morales_semana_7

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("bitcoin")
    fun getBitcoinData(): Call<BitcoinData>

    companion object {
        private const val BASE_URL = "https://mindicador.cl/api/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
