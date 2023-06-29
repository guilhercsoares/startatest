package com.sicredi.test.eventlist.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sicredi.test.eventlist.model.Event
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import com.sicredi.test.eventlist.components.CheckInBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventDetailsScreen(event: Event, performCheckIn: (String, String) -> Unit) {
    var isCheckedIn by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )
    val coroutineScope = rememberCoroutineScope()
    val spacerHeight by animateDpAsState(if (scrollState.value > 0) 16.dp else 0.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Parallax effect for the event image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(event.image).crossfade(true).build(),
            contentDescription = event.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .scrollWithParallax(scrollState)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 300.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(spacerHeight))
            Text(
                text = event.title,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = event.formattedDate(),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = event.description,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Check-in")
            }

            if (isCheckedIn) {
                Text(
                    text = "Check-in successful!",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }


        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            CheckInBottomSheet(
                onCheckIn = performCheckIn,
                onDismiss = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        }
    ) { }
}

private fun Modifier.scrollWithParallax(scrollState: ScrollState): Modifier =
    this.graphicsLayer(
        translationY = (-scrollState.value * 0.4f)
    )
