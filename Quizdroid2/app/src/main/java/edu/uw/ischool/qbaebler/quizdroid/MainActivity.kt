package edu.uw.ischool.qbaebler.quizdroid

import ViewAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var rvTopics : RecyclerView
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvTopics = findViewById(R.id.rvTopics)
        rvTopics.layoutManager = LinearLayoutManager(this)
        rvTopics.adapter = ViewAdapter(this)
    }
}