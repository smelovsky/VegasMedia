package com.example.vegas

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

@HiltViewModel
class VegasViewModel @Inject constructor(

) : ViewModel() {

    data class ForismaticOkHttpData(
        val quoteText: String,
        val quoteAuthor: String,
        val senderName: String,
        val senderLink: String,
    )

    object PicassoData {
        private const val BASE = "https://i.imgur.com/"
        private const val EXT = ".jpg"

        val URLS = arrayOf(
            BASE + "CqmBjo5" + EXT, BASE + "zkaAooq" + EXT, BASE + "0gqnEaY" + EXT,
            BASE + "9gbQ7YR" + EXT, BASE + "aFhEEby" + EXT, BASE + "0E2tgV7" + EXT,
            //BASE + "P5JLfjk" + EXT, BASE + "nz67a4F" + EXT, BASE + "dFH34N5" + EXT,
            //BASE + "FI49ftb" + EXT, BASE + "DvpvklR" + EXT, BASE + "DNKnbG8" + EXT,
            //BASE + "yAdbrLp" + EXT, BASE + "55w5Km7" + EXT, BASE + "NIwNTMR" + EXT,
            //BASE + "DAl0KB8" + EXT, BASE + "xZLIYFV" + EXT, BASE + "HvTyeh3" + EXT,
            //BASE + "Ig9oHCM" + EXT, BASE + "7GUv9qa" + EXT, BASE + "i5vXmXp" + EXT,
            //BASE + "glyvuXg" + EXT, BASE + "u6JF6JZ" + EXT, BASE + "ExwR7ap" + EXT,
            //BASE + "Q54zMKT" + EXT, BASE + "9t6hLbm" + EXT, BASE + "F8n3Ic6" + EXT,
            //BASE + "P5ZRSvT" + EXT, BASE + "jbemFzr" + EXT, BASE + "8B7haIK" + EXT,
            BASE + "aSeTYQr" + EXT, BASE + "OKvWoTh" + EXT, BASE + "zD3gT4Z" + EXT,
        )
    }

    var currentTheme: Int = 0
    var askToExitFromApp: Boolean = true
    var wakeLock: Boolean = true

    fun getMovieDefaultUri(context: Context): Uri {
        var file: File = copyFileToExternalStorage(R.raw.vegas, "vegas.mp4", context)
        val uri = Uri.fromFile(file)
        return uri
    }


    fun getPicassoItems(): PicassoData{
        return PicassoData
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // camera

    var tmp_bitmap: Bitmap? = null
    fun setTmpBitmap(bitmap: Bitmap) { tmp_bitmap = bitmap }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // forismatic

    var currentForismaticLang: Int = 0
    lateinit var forismaticCall: Call
    var http_responce = mutableStateOf("")

    fun updateForismatic() {

        initForismaticCall()

        forismaticCall.enqueue(

            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        //Looper.prepare()

                        val http_response = response.body!!.string()
                        Log.d("zzz", "http_response: " + http_response)

                        var gson = Gson()
                        var json_http_response = gson.fromJson(http_response, ForismaticOkHttpData::class.java)

                        val txt = json_http_response?.quoteText
                        var author = json_http_response?.quoteAuthor

                        if (author == "") { author = "unknown" }

                        http_responce.value = txt + " (" + author + ")"

                        //Looper.loop()
                    }
                }
            }
        )
    }

    fun initForismaticCall() {
        val forismaticClient = OkHttpClient()
        val url = if (currentForismaticLang == 0) {
            "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en"
        } else {
            "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=ru"
        }

        val formBody = FormBody.Builder()
            .build()
        val forismatic_request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()
        forismaticCall = forismaticClient.newCall(forismatic_request)
    }

}