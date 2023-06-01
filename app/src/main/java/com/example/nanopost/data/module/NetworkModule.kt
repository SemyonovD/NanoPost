package com.example.nanopost.data.module

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.nanopost.data.repository.PreferencesRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class AuthRetrofit

@Qualifier
annotation class ApiRetrofit

@Qualifier
annotation class AuthApi

@Qualifier
annotation class ProfilesApi

@Qualifier
annotation class PostsApi

@Qualifier
annotation class ImagesApi

@Qualifier
annotation class JsonIgnoreKeys

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val contentType = "application/json".toMediaType()
    private val BASE_URL = "https://nanopost.evolitist.com/api/v1/"
    private val BASE_URL_AUTH = "https://nanopost.evolitist.com/api/"

    @Provides
    @Singleton
    fun convertLongToTime (time: Long): String {
        val date = Date(time)
        return SimpleDateFormat("MMM.dd yyyy hh:mm:ss").format(date)
    }

    @Provides
    @Singleton
    fun provideSharedPref(
        @ApplicationContext appContext: Context
    ): SharedPreferences {
        return appContext.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @AuthApi
    fun provideAuthApiService(
        @AuthRetrofit retrofit: Retrofit
    ) = retrofit.create(AuthApiService::class.java)


    @Provides
    @Singleton
    @ProfilesApi
    fun provideProfileApiService(
        @ApiRetrofit retrofit: Retrofit
    ) = retrofit.create(ProfilesApiService::class.java)

    @Provides
    @Singleton
    @PostsApi
    fun providePostsApiService(
        @ApiRetrofit retrofit: Retrofit
    ) = retrofit.create(PostsApiService::class.java)

    @Provides
    @Singleton
    @ImagesApi
    fun provideImagesApiService(
        @ApiRetrofit retrofit: Retrofit
    ) = retrofit.create(ImagesApiService::class.java)

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        client: OkHttpClient,
        @JsonIgnoreKeys json: Json
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL_AUTH)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()

    @Provides
    @Singleton
    @ApiRetrofit
    fun provideApiRetrofit(
        client: OkHttpClient,
        @JsonIgnoreKeys json: Json
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()
    
    @Provides
    @Singleton
    @JsonIgnoreKeys
    fun provideJson() = Json{ignoreUnknownKeys = true}


    @Provides
    @Singleton
    fun provideApiClient(
        @ApplicationContext appContext: Context,
        tokenPrefs: PreferencesRepository
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideAuthInterceptor(tokenPrefs))
            .addInterceptor(ChuckerInterceptor(appContext))
            .build()
    }

    private fun provideAuthInterceptor(
        preferencesRepository: PreferencesRepository
    ): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            preferencesRepository.getToken()?.let { token ->
                requestBuilder.header(
                    "Authorization",
                    "Bearer $token",
                )
            }
            chain.proceed(requestBuilder.build())
        }
    }
}