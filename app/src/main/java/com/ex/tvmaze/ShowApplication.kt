package com.ex.tvmaze

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.jsoup.Jsoup

@HiltAndroidApp
class ShowApplication : Application() {
    companion object {
        fun html2text(html: String?): String? {
            return Jsoup.parse(html).text()
        }
    }
}