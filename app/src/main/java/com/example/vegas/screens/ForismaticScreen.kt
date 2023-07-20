package com.example.vegas.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.vegas.MainActivity
import com.example.vegas.R
import com.example.vegas.viewModel

@Composable
fun ForismaticScreen() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
        .fillMaxSize()
    )
    {
        Text(text = viewModel.http_responce.value,
            modifier = Modifier.verticalScroll(rememberScrollState()),
            fontSize = 22.sp
        )
    }
}

@Composable
fun MainActivity.BottomBarForismatic() {
    androidx.compose.material3.Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            viewModel.updateForismatic()
        }
    ) {
        androidx.compose.material3.Text(text = stringResource(R.string.next_forismatic))
    }
}
