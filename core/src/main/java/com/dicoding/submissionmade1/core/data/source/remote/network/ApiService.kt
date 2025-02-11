package com.dicoding.submissionmade1.core.data.source.remote.network


import com.dicoding.submissionmade1.core.data.source.remote.response.DetailEventsResponse
import com.dicoding.submissionmade1.core.data.source.remote.response.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    suspend fun getListEvents(@Query("active") query: Int): EventsResponse

    @GET("events")
    suspend fun searchEvents(
        @Query("active") active: Int,
        @Query("q") query: String,
    ): EventsResponse

    @GET("events/{id}")
    suspend fun getDetailEvents(@Path("id") id: Int): DetailEventsResponse

    @GET("events")
    suspend fun dailyReminder(
        @Query("active") active: Int,
        @Query("limit") limit: Int,
    ): EventsResponse
}