package com.ahuaman.videobackgroundcomposable

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.ahuaman.videobackgroundcomposable.ui.theme.VideoBackgroundComposableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoBackgroundComposableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoBackground()
                }
            }
        }
    }
}


@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoBackground() {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    exoPlayer.playWhenReady = true

    //Loop video
    exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL

    //Set media uri from local storage raw background video
    val uri  = RawResourceDataSource.buildRawResourceUri(R.raw.background_beach)

    val mediaItem = remember {
        MediaItem.Builder()
            .setUri(uri)
            .build()
    }

    exoPlayer.setMediaItem(mediaItem)

    exoPlayer.prepare()

    //Set Ui with playerView

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = {
            PlayerView(it).apply {
                useController = false
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        })


        //more composables
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "SIGN IN")
            }
        }
    }

}