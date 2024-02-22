package edu.uw.ischool.qbaebler.quizdroid

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast


class DownloadService: Service() {
 
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra("url")
            if (testConnection() == "success") {
                downloadFile(
                    applicationContext,
                    "http://tednewardsandbox.site44.com/questions.json"
                )
            }
        return START_NOT_STICKY
    }



    fun testConnection(): String {
        Log.i("test", "testing connection")
        if (!isConnected()) {
            // Display appropriate message based on connectivity status
            return if (isAirplaneMode()) {
                askToDisableAirplaneMode()
                "error"
            } else {
                displayErrorMessage("No internet connection available.")
                "error"
            }
        }
        Log.i("SuccessfulConnection", "WEEEREIRUEORUOIWE")
        return "success"
    }
    private fun isConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun isAirplaneMode(): Boolean {
        return Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON) != 0
    }

    private fun askToDisableAirplaneMode() {
        Toast.makeText(this, "Airplane Mode on... Redirecting.", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {

            Toast.makeText(this, "Unable to open Airplane Mode settings.", Toast.LENGTH_LONG).show()
        }
    }
    private fun displayErrorMessage(message: String) {
        // Display an error message to the user
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("Range")
    fun downloadFile(context: Context, url: String?) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Questions")
            .setDescription("Downloading JSON file")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(context, null, "questions.json")

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        // Extra Credit: Notifys user when attempting to download JSON
        Toast.makeText(context, "Attempting to Download JSON", Toast.LENGTH_SHORT).show()
        val downloadId = downloadManager.enqueue(request)

        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val downloadBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val receivedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (receivedDownloadId == downloadId) {
                    val status = getDownloadStatus(context, downloadId)
                    handleDownloadStatus(context, status)
                    context.unregisterReceiver(this)
                }
            }
        }
        context.registerReceiver(downloadBroadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    @SuppressLint("Range")
    private fun getDownloadStatus(context: Context, downloadId: Long): Int {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val cursor = downloadManager.query(query)
        return if (cursor.moveToFirst()) {
            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
        } else {
            -1 // Indicate error or not found
        }
    }

    private fun handleDownloadStatus(context: Context, status: Int) {
        val intent = Intent(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        intent.putExtra("status", status)
        context.sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}






