package com.straxfromibr.jetpackghapi.model.remote_data_source

import javax.inject.Inject
import kotlinx.serialization.ExperimentalSerializationApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


class ApiClientProvider @Inject constructor() {
    companion object {
        private const val API_END_POINT = "https://api.github.com/"
    }

    @ExperimentalSerializationApi
    fun provide(): ApiClient
    {
        return Retrofit.Builder()
            .baseUrl(API_END_POINT)
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(
                    "application/json".toMediaType()
                ),
            )
            .build()
            .create(ApiClient::class.java)

    }
}