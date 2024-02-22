package edu.uw.ischool.qbaebler.quizdroid

import ViewAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var rvTopics : RecyclerView
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // var url = intent.getStringExtra("URL")
       // var dInterval = intent.getStringExtra("INTERVAL")

        setSupportActionBar(findViewById(R.id.toolbar))
        rvTopics = findViewById(R.id.rvTopics)
        rvTopics.layoutManager = LinearLayoutManager(this)
        QuizApp.quizData.createJSON(this)
        rvTopics.adapter = ViewAdapter(QuizApp.quizData)
    }


}