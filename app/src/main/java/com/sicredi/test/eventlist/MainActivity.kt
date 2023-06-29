package com.sicredi.test.eventlist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.sicredi.test.eventlist.api.EventApiService
import com.sicredi.test.eventlist.model.Event
import com.sicredi.test.eventlist.screens.EventDetailsActivity
import com.sicredi.test.eventlist.screens.EventListScreen
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val eventApiService = EventApiService.create()
    private val eventsState = mutableStateOf(emptyList<Event>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EventListScreen(events = eventsState.value) { event ->
                fetchDetails(event)
            }
        }

        fetchEvents()
    }

    private fun onEventItemClick(event: Event) {
        EventDetailsActivity.start(this, event)
    }

    private fun fetchDetails(event: Event) {
        lifecycleScope.launch {
            try {
                val response = eventApiService.getEventDetails(eventId = event.id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        onEventItemClick(it)
                    }
                } else {
                    // Handle error response
                    // RECORD TO FIREBASE
                }
            } catch (e: IOException) {
                // Handle network error
                // RECORD TO FIREBASE
            } catch (e: HttpException) {
                // Handle HTTP error
                // RECORD TO FIREBASE
            }
        }
    }

    private fun fetchEvents() {
        lifecycleScope.launch {
            try {
                val response = eventApiService.getEvents()
                if (response.isSuccessful) {
                    val eventList = response.body()
                    if (eventList != null) {
                        eventsState.value = eventList
                    } else {
                        // Handle empty response
                        // RECORD TO FIREBASE
                    }
                } else {
                    // Handle error response
                    // RECORD TO FIREBASE
                }
            } catch (e: IOException) {
                // Handle network error
                // RECORD TO FIREBASE
            } catch (e: HttpException) {
                // Handle HTTP error
                // RECORD TO FIREBASE
            }
        }
    }
}

