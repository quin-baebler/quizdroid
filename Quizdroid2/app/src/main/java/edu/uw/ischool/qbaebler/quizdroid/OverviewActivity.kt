package edu.uw.ischool.qbaebler.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.TextView

class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val overviewText = findViewById<TextView>(R.id.description)
        val beginButton = findViewById<Button>(R.id.beginButton)
        val subjectTitle = findViewById<TextView>(R.id.subjectTitle)

        val topic = intent.getStringExtra(ViewHolder.TOPIC_NAME)
        val overview = intent.getStringExtra(ViewHolder.TOPIC_OVERVIEW)
        val questions = intent.getStringArrayExtra(ViewHolder.TOPIC_QUESTIONS)
        val answerOptions = intent.getStringArrayExtra(ViewHolder.ANSWER_OPTIONS)
        val correctAnswers = intent.getStringArrayExtra(ViewHolder.CORRECT_ANSWERS)

        subjectTitle.text = topic
        overviewText.text = overview
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)

            intent.putExtra("TOPIC", topic)
            intent.putExtra("QUESTIONS", questions)
            intent.putExtra("ANSWER_OPTIONS", answerOptions)
            intent.putExtra("CORRECT_ANSWERS", correctAnswers)
            intent.putExtra("NUMBER_CORRECT", 0)
            intent.putExtra("INDEX", 0)

            startActivity(intent)
        }
    }
}