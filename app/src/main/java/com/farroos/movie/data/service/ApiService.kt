package com.farroos.movie.data.service

import com.farroos.movie.BuildConfig
import com.farroos.movie.data.remote.home.MovieResponse
import com.farroos.movie.data.remote.detail.DetailMovie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("movie/now_playing")
    fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MovieResponse>

    @GET("movie/{movieId}")
    fun getDetailMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<DetailMovie>

/*
    companion object {
        @JvmStatic
        operator fun invoke(): ApiService {
            val authInterceptor = Interceptor {
                val originRequest = it.request()
                val oldUrl = originRequest.url
                val newUrl = oldUrl.newBuilder().apply {
                    addQueryParameter("api_key", BuildConfig.API_KEY)
                }.build()
                it.proceed(originRequest.newBuilder().url(newUrl).build())
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiService::class.java)

        }
    }
*/
}

object ApiClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        print(level.toString())
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofit.create(ApiService::class.java)
    }

}
