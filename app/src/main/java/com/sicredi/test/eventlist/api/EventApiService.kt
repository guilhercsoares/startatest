package com.sicredi.test.eventlist.api

import com.sicredi.test.eventlist.model.CheckInPayload
import com.sicredi.test.eventlist.model.CheckInResponse
import com.sicredi.test.eventlist.model.Event
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApiService {

    @GET("events")
    suspend fun getEvents(): Response<ArrayList<Event>>

    @GET("events/{eventId}")
    suspend fun getEventDetails(@Path("eventId") eventId: String): Response<Event>

    @POST("checkin")
    suspend fun doCheckIn(@Body checkInPayload: CheckInPayload): Response<CheckInResponse>

    companion object {
        private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

        fun create(): EventApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(EventApiService::class.java)
        }
    }
}
