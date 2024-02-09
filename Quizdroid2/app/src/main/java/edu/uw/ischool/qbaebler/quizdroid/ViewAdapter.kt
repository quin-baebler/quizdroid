import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uw.ischool.qbaebler.quizdroid.OverviewActivity
import edu.uw.ischool.qbaebler.quizdroid.R

private val topics = listOf("Math", "Physics", "Marvel Super Heroes", "Sports")
private val descs = listOf("Add it up!", "E = MC^2", "Marvel or DC?", "Whats your favorite sport?")


class ViewAdapter(val activity : Activity) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).
            inflate(R.layout.activity_topic, parent, false))
    }
    override fun getItemCount(): Int {
        return topics.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.texttopic).text = topics[position]
        holder.topics = topics[position]
        holder.overviews = descs[position]
        holder.questions = arrayOf("What is 9 + 10?","What is 2x2","What is 8 - 5")
        holder.answerOptions = arrayOf("19","2","3","4")
        holder.correctAnswers = arrayOf("19","4","3")
    }
}

class ViewHolder(val view: View, var topics: String? = null,
                 var overviews: String? = null, var questions: Array<String>? = null,
                 var answerOptions: Array<String>? = null,
                 var correctAnswers: Array<String>? = null) : RecyclerView.ViewHolder(view) {

    companion object {
        val TOPIC_NAME = "TOPIC_NAME"
        val TOPIC_OVERVIEW = "TOPIC_OVERVIEW"
        val TOPIC_QUESTIONS = "TOPIC_QUESTION"
        val ANSWER_OPTIONS = "ANSWER_OPTIONS"
        val CORRECT_ANSWERS = "CORRECT_ANSWERS"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, OverviewActivity::class.java)

            intent.putExtra(TOPIC_NAME, topics)
            intent.putExtra(TOPIC_OVERVIEW, overviews)
            intent.putExtra(TOPIC_QUESTIONS, questions)
            intent.putExtra(ANSWER_OPTIONS, answerOptions)
            intent.putExtra(CORRECT_ANSWERS, correctAnswers)

            view.context.startActivity(intent)
        }
    }
}

