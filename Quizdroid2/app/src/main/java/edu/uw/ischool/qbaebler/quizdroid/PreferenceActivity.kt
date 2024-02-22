package edu.uw.ischool.qbaebler.quizdroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class PreferenceActivity : AppCompatActivity() {
    var isDownloading: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        var dataURL = findViewById<EditText>(R.id.urlText).text
        var dInterval = findViewById<EditText>(R.id.downloadInterval).text
        Log.i("create URL", "$dataURL")
        editor.putString("download_url", dataURL.toString())
        editor.putString("download_interval", dInterval.toString())
        editor.apply()

        var backButton = findViewById<Button>(R.id.updateButton)
        backButton.setOnClickListener(){
            val intent = Intent(this, StartupActivity::class.java)
            intent.putExtra("URL", dataURL)
            startActivity(intent)


            //intent.putExtra("INTERVAL", dInterval)
        }
    }
}