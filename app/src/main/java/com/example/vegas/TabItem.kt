package com.example.vegas

import androidx.compose.runtime.Composable
import com.example.vegas.screens.CameraScreen
import com.example.vegas.screens.ForismaticScreen
import com.example.vegas.screens.HomeScreen
import com.example.vegas.screens.MovieScreen
import com.example.vegas.screens.PicassoScreen
import com.example.vegas.screens.SettingsScreen

data class ScreenParams(
    val permissionsGranted: Boolean,
    val INTERNET: Boolean,
    val ACCESS_NETWORK_STATE: Boolean,
    val WAKE_LOCK: Boolean,
    val ACCESS_NOTIFICATION_POLICY: Boolean,
    val CAMERA: Boolean,
    val RECEIVE_BOOT_COMPLETED: Boolean,
    val READ_EXTERNAL_STORAGE: Boolean,
    val WRITE_EXTERNAL_STORAGE: Boolean,
    val showPicture: Boolean,
    val selectedPicasso: String,
)
typealias ComposableFun = @Composable (screenParams: ScreenParams) -> Unit

sealed class TabItem(var icon: Int, var title: Int, var screen: ComposableFun) {
    object Home : TabItem(R.drawable.ic_label, R.string.tab_name_home, { HomeScreen(
        it.permissionsGranted,
        it.INTERNET,
        it.ACCESS_NETWORK_STATE,
        it.WAKE_LOCK,
        it.ACCESS_NOTIFICATION_POLICY,
        it.CAMERA,
        it.RECEIVE_BOOT_COMPLETED,
        it.READ_EXTERNAL_STORAGE,
        it.WRITE_EXTERNAL_STORAGE,
    ) } )
    object Camera : TabItem(R.drawable.ic_label, R.string.tab_name_camera, { CameraScreen(it.showPicture) } )
    object Movie : TabItem(R.drawable.ic_label, R.string.tab_name_movie, { MovieScreen() } )
    object Picasso : TabItem(R.drawable.ic_label, R.string.tab_name_picasso, { PicassoScreen(it.selectedPicasso) } )
    object Forismatic : TabItem(R.drawable.ic_label, R.string.tab_name_forismatic, { ForismaticScreen() } )
    object Settings : TabItem(R.drawable.ic_label, R.string.tab_name_settings, { SettingsScreen() } )
}