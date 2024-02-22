package edu.uw.ischool.qbaebler.quizdroid


import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class StartupActivity : AppCompatActivity() {

    private val downloadBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val status = intent.getIntExtra("status", -1)
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                // Extra Credit: Notify user if failed/success
                Toast.makeText(context, "Success!!!", Toast.LENGTH_SHORT).show()
            } else if (status == DownloadManager.STATUS_FAILED) {
                Toast.makeText(context, "JSON Download Failed", Toast.LENGTH_SHORT).show()
                showDownloadErrorDialog()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        setSupportActionBar(findViewById(R.id.my_toolbar));

        startService(Intent(this, DownloadService::class.java))
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val url = sharedPreferences.getString("download_url", "")
        // Start the DownloadService with the application context
        startService(Intent(this, DownloadService::class.java).apply {
            putExtra("url", url)
        })

        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        registerReceiver(downloadBroadcastReceiver, intentFilter)

        val button = findViewById<Button>(R.id.topicButton)
        button.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showDownloadErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Download Failed")
        builder.setMessage("The download of questions failed. Do you want to retry or go to the home screen?")
        builder.setPositiveButton("Retry") { _, _ ->
            val downloadIntent = Intent(this, DownloadService::class.java)
            startService(downloadIntent)
        }
        builder.setNegativeButton("Home Screen") { _, _ ->
            System.exit(0)
        }
        builder.show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_preferences) {
            startActivity(Intent(this, PreferenceActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}