
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uw.ischool.qbaebler.quizdroid.OverviewActivity
import edu.uw.ischool.qbaebler.quizdroid.QuizApp
import edu.uw.ischool.qbaebler.quizdroid.R
import edu.uw.ischool.qbaebler.quizdroid.TopicData




class ViewAdapter(val quiz : TopicData) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_topic, parent, false),
            quiz.topicData[0]
        )
    }

    override fun getItemCount(): Int {
        return quiz.topicData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.texttopic).text = quiz.topicData[position].title
        holder.view.findViewById<TextView>(R.id.shortDesc).text = quiz.topicData[position].shortDesc
        holder.overviews = quiz.topicData[position].longDesc
        holder.questions = quiz.topicData[position].questions
        holder.topic = quiz.topicData[position]
    }
}
class ViewHolder(val view: View,
                 var topic: QuizApp.Topic,
                 var overviews: String? = null,
                 var questions: ArrayList<QuizApp.Question>? = null) : RecyclerView.ViewHolder(view){

    companion object {
        val TOPIC_DATA = "TOPIC_DATA"
        val TOPIC_OVERVIEW = "TOPIC_OVERVIEW"
        val TOPIC_QUESTIONS = "TOPIC_QUESTION"
        val IMAGE_ICON = "IMAGE_ICON"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, OverviewActivity::class.java)

            intent.putExtra(TOPIC_DATA, topic)
            intent.putExtra(TOPIC_OVERVIEW, overviews)
            intent.putExtra(TOPIC_QUESTIONS, questions)
            view.context.startActivity(intent)
        }
    }
}
