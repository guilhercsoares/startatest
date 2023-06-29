package com.sicredi.test.eventlist.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.sicredi.test.eventlist.api.EventApiService
import com.sicredi.test.eventlist.model.CheckInPayload
import com.sicredi.test.eventlist.model.Event
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class EventDetailsActivity : AppCompatActivity() {
    private val eventApiService = EventApiService.create()

    companion object {
        private const val EXTRA_EVENT = "extra_event"

        fun start(context: Context, event: Event) {
            val intent = Intent(context, EventDetailsActivity::class.java).apply {
                putExtra(EXTRA_EVENT, event)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val event = intent.getParcelableExtra<Event>(EXTRA_EVENT) ?: return

        setContent {
            EventDetailsScreen(event = event) { name, email ->
                lifecycleScope.launch {
                    try {
                        val response = eventApiService.doCheckIn(
                            CheckInPayload(
                                eventId = event.id,
                                name = name,
                                email = email
                            )
                        )

                        if (response.isSuccessful) {
                            response.body()?.let { checkInResponse ->
                                checkInResponse.code?.let {
                                    if (it.toInt() == 200) {
                                        // SUCCESS
                                        AlertDialog.Builder(this@EventDetailsActivity).apply {
                                            this.setTitle("Check-In")
                                            this.setMessage("Check-In realizado com sucesso")
                                            this.setPositiveButton("OK") { _, _ -> }
                                            this.show()
                                        }
                                    } else {
                                        // ERROR: SHOW MESSAGE
                                        AlertDialog.Builder(this@EventDetailsActivity).apply {
                                            this.setTitle("Check-In")
                                            this.setMessage("Não foi possível realizar o check-in no momento. Tente novamente mais tarde.")
                                            this.setPositiveButton("OK") { _, _ -> }
                                            this.show()
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        println(e.message)
                        AlertDialog.Builder(this@EventDetailsActivity).apply {
                            this.setTitle("Check-In")
                            this.setMessage("Não foi possível realizar o check-in no momento. Tente novamente mais tarde.")
                            this.setPositiveButton("OK") { _, _ -> }
                            this.show()
                        }
                    }
                }
            }
        }
    }
}
