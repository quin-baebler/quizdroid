package edu.uw.ischool.qbaebler.quizdroid

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Environment
import android.util.Log
import android.view.Display.Mode
import org.json.JSONArray
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

// implementation of TopicRepository to store hardcoded data
class TopicData: QuizApp.TopicRepository {
    val topicData = arrayListOf<QuizApp.Topic>()
    override fun getTopics(): ArrayList<QuizApp.Topic> {
       return this.topicData
    }

    override fun addTopic(t: QuizApp.Topic) {
        this.topicData.add(t)
    }

    override fun removeTopic(t: QuizApp.Topic) {
        this.topicData.remove(t)
    }

    fun getFile(context: Context): InputStream? {
        return try {
            context.assets.open("questions.json")
        } catch (e: IOException) {
            Log.e("TopicRepository", "Error opening file: $e")
            null
        }
    }

    fun createJSON(context: Context){
        val sharedPrefs = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val url = sharedPrefs.getString("download_url", "")
        //val interval = sharedPrefs.getInt("download_interval", 0)
        val file = getFile(context)
        if (url != "") {
            // download from url
        }

        if (file != null) {
            val text = file.reader().use { it.readText() }
            val jsonArray = JSONArray(text)
            Log.i("testLength", "${jsonArray.length()}")

            for (i in 0..jsonArray.length() - 1) {
                val topic = jsonArray.getJSONObject(i)
                val title = topic.getString("title")
                val desc = topic.getString("desc")
                val questions = topic.getJSONArray("questions")
                val formattedQuestion = ArrayList<QuizApp.Question>()
                for (j in 0..questions.length() - 1) {
                    val question = questions.getJSONObject(j)
                    val text = question.getString("text")
                    val correctIndex = question.getInt("answer") - 1
                    val answers = question.getJSONArray("answers")
                    val formattedAnswers = ArrayList<String>()
                    for (k in 0..answers.length() - 1) {
                        formattedAnswers.add(answers[k].toString())
                    }
                    formattedQuestion.add(QuizApp.Question(text, formattedAnswers, correctIndex))
                }
                this.addTopic(QuizApp.Topic(title, desc, desc, formattedQuestion))
            }
        }
    }

    fun addSampleData() {
        val mathq1 = QuizApp.Question("What is 4 x 3", arrayListOf("10", "12", "13", "40"), 1)
        val mathq2 = QuizApp.Question("Which is not a prime number", arrayListOf("2", "3", "5", "9"), 3)
        val scienceq1 = QuizApp.Question("How many Newton's Laws are there?", arrayListOf("1", "2", "3", "4"), 2)
        val heroq1 = QuizApp.Question("Which is not a Marvel Hero", arrayListOf("Black Panther", "Captain America", "Batman", "Thor"), 2)
        val sportsq1 = QuizApp.Question("Where is Messi from?", arrayListOf("Spain", "Brazil", "Germany", "Argentina"), 3)

        val math = QuizApp.Topic("Math", "Numbers", "Add it up!", arrayListOf(mathq1, mathq2))
        val physics = QuizApp.Topic("Physics", "Newton", "Newton and his laws",arrayListOf(scienceq1))
        val heroes = QuizApp.Topic("Marvel Super Heroes", "Heroes", "Marvel or DC?", arrayListOf(heroq1))
        val sports = QuizApp.Topic("Sports", "Sports", "Which is the best sport?", arrayListOf(sportsq1))

        if (this.topicData.size == 0) {
            this.addTopic(math)
            this.addTopic(physics)
            this.addTopic(heroes)
            this.addTopic(sports)
        }
    }
}
