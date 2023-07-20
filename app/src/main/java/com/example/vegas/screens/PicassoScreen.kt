package com.example.vegas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.example.vegas.MainActivity
import com.example.vegas.viewModel
import coil.compose.SubcomposeAsyncImage
import androidx.compose.ui.geometry.Size

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicassoScreen(selectedPicasso: String) {

    Column(
        modifier = Modifier
            .background(Color.DarkGray)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        SubcomposeAsyncImage(
            model = selectedPicasso,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            loading = {
                CircularProgressIndicator()
            },
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivity.BottomBarPicasso() {

    var mExpanded by remember { mutableStateOf(false) }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (!mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            //.background(colorResource(id = R.color.purple_700))
            .wrapContentSize(Alignment.TopCenter)
    ) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
            ),

            readOnly = true,
            value = selectedPicasso.value,
            onValueChange = { selectedPicasso.value = it },

            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = {Text("URL")},
            trailingIcon = {
                Icon(
                    icon,"",
                    Modifier.clickable { mExpanded = !mExpanded },
                    Color.White
                )
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) {
                mTextFieldSize.width.toDp()
            })
        ) {
            viewModel.getPicassoItems().URLS.forEach { url ->
                DropdownMenuItem(onClick = {
                    selectedPicasso.value = url
                    mExpanded = false
                }) {
                    Text(text = url)
                }
            }
        }

    }
}
