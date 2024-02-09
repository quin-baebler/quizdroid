package edu.uw.ischool.qbaebler.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val topic = intent.getStringExtra("TOPIC")
        val questions = intent.getStringArrayExtra("QUESTIONS")
        val answers = intent.getStringArrayExtra("ANSWER_OPTIONS")
        val correctAnswers = intent.getStringArrayExtra("CORRECT_ANSWERS")
        var numberCorrect = intent.getIntExtra("NUMBER_CORRECT", 0)
        var i = intent.getIntExtra("INDEX", 0)
        var selected = ""
        var sButton = findViewById<Button>(R.id.submitButton)

        findViewById<TextView>(R.id.quizQuestion).text = questions!![i]
        findViewById<RadioButton>(R.id.ranswer_1).text = answers!![0]
        findViewById<RadioButton>(R.id.ranswer_2).text = answers[1]
        findViewById<RadioButton>(R.id.ranswer_3).text = answers[2]
        findViewById<RadioButton>(R.id.ranswer_4).text = answers[3]

        findViewById<RadioGroup>(R.id.radioGroup).setOnCheckedChangeListener {_, checkedID  ->
           sButton.isEnabled = true
            selected = findViewById<RadioButton>(checkedID).text.toString()
        }

        sButton.setOnClickListener {
            val intent = Intent(this, AnswerActivity::class.java)

            if (selected == answers[i]) {
                numberCorrect++
            }

            i++
            intent.putExtra("TOPIC", topic)
            intent.putExtra("QUESTIONS", questions)
            intent.putExtra("ANSWER_OPTIONS", answers)
            intent.putExtra("CORRECT_ANSWERS", correctAnswers)
            intent.putExtra("NUMBER_CORRECT", numberCorrect)
            intent.putExtra("INDEX", i)
            intent.putExtra("SELECTED", selected)

            startActivity(intent)
        }




    }
}