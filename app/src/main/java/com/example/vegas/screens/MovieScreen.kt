package com.example.vegas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.vegas.MainActivity
import com.example.vegas.viewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun MovieScreen() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color.Black))
    {
        val context = LocalContext.current

        val exoPlayer = ExoPlayer.Builder(LocalContext.current)
            .build()
            .also { exoPlayer ->
                val mediaItem = MediaItem.Builder()
                    .setUri(viewModel.getMovieDefaultUri(context))
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }

        DisposableEffect(

            AndroidView(factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            })
        ) {
            onDispose { exoPlayer.release() }
        }
    }
}

@Composable
fun MainActivity.BottomBarMovie() {

    val context = LocalContext.current

    Row() {

        androidx.compose.material3.Text(text = "URI: ")
        androidx.compose.material3.Text(text = viewModel.getMovieDefaultUri(context).toString())

    }
}