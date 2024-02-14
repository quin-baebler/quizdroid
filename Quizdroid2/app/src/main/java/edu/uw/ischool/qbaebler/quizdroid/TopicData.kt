package edu.uw.ischool.qbaebler.quizdroid

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