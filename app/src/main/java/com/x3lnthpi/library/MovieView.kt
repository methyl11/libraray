package com.x3lnthpi.library

//import android.media.browse.MediaBrowser.MediaItem
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.ui.AspectRatioFrameLayout

//@OptIn(UnstableApi::class)
//@Composable
//fun VideoPlayerScreen() {
//    val context = LocalContext.current
//    var player by remember { mutableStateOf<ExoPlayer?>(null) }
//
//
//    DisposableEffect(Unit) {
//        player = ExoPlayer.Builder(context).build()
//        onDispose {
//            player?.release()
//            player = null
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        player?.let {
//            val mediaItem = MediaItem.fromUri(Uri.parse("https://storage.googleapis.com/movie-x-dash/Brightburn/brightburn.mpd"))
//            val dataSourceFactory = DefaultHttpDataSource.Factory()
//            val mediaSource = DashMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
//            it.setMediaSource(mediaSource)
//            it.prepare()
//            it.play()
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        player?.let {
//            AndroidView(
//                factory = {
//                    PlayerView(context).apply {
//                        useController = true
//                        player = it as ExoPlayer?
//                    }
//                },
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
//}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen() {
    val context = LocalContext.current
    var player by remember { mutableStateOf<ExoPlayer?>(null) }

    // Initialize and release the player
    DisposableEffect(Unit) {
        player = ExoPlayer.Builder(context).build()
        onDispose {
            player?.release()
            player = null
        }
    }

    // Prepare and play the media
    LaunchedEffect(Unit) {
        player?.let {
            val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/movie-x-dash/Brightburn/test.mpd")
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val mediaSource = DashMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
            it.setMediaSource(mediaSource)
            it.prepare()
            it.play()
        }
    }

    // Display the player UI
    Box() {
        player?.let { exoPlayer ->
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    PlayerView(context).apply {
                        useController = true
                        player = exoPlayer // Set the ExoPlayer instance here
                        // Ensure the surface is set up for rendering
                        useArtwork = false
                        useController = true
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    }
                },
                //modifier = Modifier.fillMaxSize()
                update = { playerView ->
                    playerView.player = player
                }
            )
        }
    }
}