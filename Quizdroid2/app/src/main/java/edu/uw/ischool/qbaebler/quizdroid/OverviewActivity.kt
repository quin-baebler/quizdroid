package edu.uw.ischool.qbaebler.quizdroid

import ViewHolder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val overviewText = findViewById<TextView>(R.id.description)
        val beginButton = findViewById<Button>(R.id.beginButton)
        val subjectTitle = findViewById<TextView>(R.id.subjectTitle)

        val topic = intent.getParcelableExtra<QuizApp.Topic>(ViewHolder.TOPIC_DATA)
        val questions = intent.getParcelableArrayListExtra<QuizApp.Question>(ViewHolder.TOPIC_QUESTIONS)

        subjectTitle.text = topic!!.title
        overviewText.text = topic.longDesc

        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)

            intent.putExtra("TOPIC", topic)
            intent.putExtra("QUESTIONS", questions)

            intent.putExtra("NUMBER_CORRECT", 0)
            intent.putExtra("INDEX", 0)

            startActivity(intent)
        }
    }
}