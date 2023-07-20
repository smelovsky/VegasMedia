package com.example.vegas.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.example.vegas.MainActivity
import com.example.vegas.R
import com.example.vegas.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CameraScreen(showPicture: Boolean) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        if (showPicture) {
            Image(viewModel.tmp_bitmap!!.asImageBitmap(), null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
        }

    }
}

@Composable
fun MainActivity.BottomBarCamera() {
    val launcher_take_picture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()) {
        if (it != null) {
            viewModel.setTmpBitmap(it)
            showPicture.value = true
        }
    }

    androidx.compose.material3.Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            showPicture.value = false
            launcher_take_picture.launch()
        }
    ) {
        androidx.compose.material3.Text(text = stringResource(R.string.take_a_picture))
    }
}
