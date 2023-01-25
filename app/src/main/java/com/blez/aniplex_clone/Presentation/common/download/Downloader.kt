package com.blez.aniplex_clone.Presentation.common.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.blez.aniplex_clone.R
import com.google.android.exoplayer2.util.MimeTypes

class Downloader(context : Context) {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    val appname  = context.getString(R.string.app_name)
    fun downloadVideo(url : String,downloadTitle : String) : Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(downloadTitle)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,downloadTitle)
            return downloadManager.enqueue(request)
    }
}