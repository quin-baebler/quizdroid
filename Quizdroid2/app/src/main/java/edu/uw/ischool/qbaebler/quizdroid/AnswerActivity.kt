package edu.uw.ischool.qbaebler.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val topic = intent.getParcelableExtra<QuizApp.Topic>("TOPIC")
        val questions = intent.getParcelableArrayListExtra<QuizApp.Question>("QUESTIONS")
        val numberCorrect = intent.getIntExtra("NUMBER_CORRECT", 0)
        var i = intent.getIntExtra("INDEX", -1)
        val selectedAnswer = intent.getStringExtra("SELECTED")
        val correctAnswer = questions!![i].answers!![questions[i].correctIndex]
        i++

        val nextButton =  findViewById<Button>(R.id.nextButton)

        findViewById<TextView>(R.id.userAnswer).text = "Your answer: $selectedAnswer"
        findViewById<TextView>(R.id.correctAnswer).text = "Correct answer: ${correctAnswer}"
        findViewById<TextView>(R.id.num_correct).text = "You have $numberCorrect of ${questions!!.size} correct"

        if (i >= questions.size) {
            nextButton.text = "Finish"
            nextButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            nextButton.text = "next"
            nextButton.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java)

                intent.putExtra("TOPIC", topic)
                intent.putExtra("QUESTIONS", questions)
                intent.putExtra("NUMBER_CORRECT", numberCorrect)
                intent.putExtra("INDEX", i)
                startActivity(intent)
            }
        }
    }
}