//package com.x3lnthpi.library
//
//import android.content.Context
//import android.net.Uri
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.media3.common.MediaItem
//import androidx.media3.common.Player
//import androidx.media3.datasource.DefaultHttpDataSource
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.exoplayer.dash.DashMediaSource
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class MovieViewModel() : ViewModel() {
//    // ExoPlayer instance
//    private var exoPlayer: ExoPlayer? = null
//
//    // State to track if the player is initialized
//    private val _isPlayerInitialized = MutableStateFlow(false)
//    val isPlayerInitialized: StateFlow<Boolean> get() = _isPlayerInitialized
//
//    // Initialize the player
//    fun initializePlayer(context: Context) {
//        exoPlayer = ExoPlayer.Builder(context).build().apply {
//            // Add listener to handle playback events
//            addListener(object : Player.Listener {
//                override fun onPlayerError(error: Player.PlaybackException) {
//                    // Handle playback errors
//                    println("Playback error: ${error.message}")
//                }
//
//                override fun onRenderedFirstFrame() {
//                    // Track when the first frame is rendered
//                    println("First frame rendered")
//                }
//            })
//        }
//        _isPlayerInitialized.value = true
//    }
//
//    // Prepare and play the media
//    fun playMedia(mediaUrl: String) {
//        exoPlayer?.let { player ->
//            val mediaItem = MediaItem.fromUri(Uri.parse(mediaUrl))
//            val dataSourceFactory = DefaultHttpDataSource.Factory()
//            val mediaSource = DashMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
//            player.setMediaSource(mediaSource)
//            player.prepare()
//            player.play()
//        }
//    }
//
//    // Release the player when no longer needed
//    override fun onCleared() {
//        exoPlayer?.release()
//        exoPlayer = null
//        super.onCleared()
//    }
//
//    // Get the ExoPlayer instance
//    fun getPlayer(): ExoPlayer? = exoPlayer
//
//}