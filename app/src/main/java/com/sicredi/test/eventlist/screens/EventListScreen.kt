package com.sicredi.test.eventlist.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.sicredi.test.eventlist.model.Event

@Composable
fun EventListScreen(events: List<Event>, onItemClick: (Event) -> Unit) {
    if (events.isEmpty()) {
        Text(text = "Loading events...")
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(events) { event ->
                EventListItem(event = event) {
                    onItemClick(event)
                }
            }
        }
    }
}