package com.ex.tvmaze

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ex.tvmaze.common.database.ShowDatabase
import dagger.hilt.android.HiltAndroidApp
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.Exception
import java.net.InetAddress
import kotlin.properties.Delegates
import android.net.ConnectivityManager

import android.net.NetworkInfo




@HiltAndroidApp
class ShowApplication : Application() {
    companion object {
        lateinit var database: ShowDatabase
        var haveInternet = false
        fun html2text(html: String?): String? {
            return Jsoup.parse(html).text()
        }

        fun checkConnection(context: Context): Boolean {
            val connMgr =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connMgr != null) {
                val activeNetworkInfo = connMgr.activeNetworkInfo
                if (activeNetworkInfo != null) { // connected to the internet
                    return if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                        return true
                    } else activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
                }
            }
            return false
        }
    }




    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, ShowDatabase::class.java, "ShowDatabase").build()
    }
}