package edu.uw.ischool.qbaebler.quizdroid

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import android.util.Log



// Had to do some research onto creating Parcelables and Android Studio ended up auto generating
// most of the parcelable methods
class QuizApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("QuizApp", "Application is running")
    }

    interface TopicRepository {
        fun getTopics(): List<Topic>
        fun addTopic(t: Topic)
        fun removeTopic(t: Topic)
    }

    companion object {
        val quizData = TopicData()
    }

    data class Topic(
        val title: String,
        val shortDesc: String,
        val longDesc: String,
        val questions: ArrayList<Question>
    ) : Parcelable {
        // Had to create a parcelable class for passing Topic as intent
        // Used a combination stack overflow and bard to learn how to do this so I didn't have
        // to switch to fragments
        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.createTypedArrayList(Question.CREATOR)!!
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(shortDesc)
            parcel.writeString(longDesc)
            parcel.writeTypedList(questions)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Topic> {
            override fun createFromParcel(parcel: Parcel): Topic {
                return Topic(parcel)
            }

            override fun newArray(size: Int): Array<Topic?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Question(
        val qText: String?,
        val answers: java.util.ArrayList<String>?,
        val correctIndex: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.readInt()

        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(qText)
            parcel.writeStringList(answers)
            parcel.writeInt(correctIndex)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Question> {
            override fun createFromParcel(parcel: Parcel): Question {
                return Question(parcel)
            }

            override fun newArray(size: Int): Array<Question?> {
                return arrayOfNulls(size)
            }
        }
    }
}


