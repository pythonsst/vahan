import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.vahan.R
import com.task.vahan.University

class UniversityAdapter(
    private var universities: List<University>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<UniversityAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)
        val websiteTextView: TextView = itemView.findViewById(R.id.websiteTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val university = universities[position]
        holder.nameTextView.text = university.name
        holder.countryTextView.text = university.country
        holder.websiteTextView.text = university.web_pages.toString();



        // Set an onClick listener for the website TextView
        holder.websiteTextView.text = ("${university.web_pages.toString().replace("[", "").replace("]", "")}");

        holder.websiteTextView.setOnClickListener {
            val url = university.web_pages.toString().replace("[", "").replace("]", "");
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return universities.size
    }

    fun updateData(universities: List<University>) {
        this.universities = universities
        notifyDataSetChanged()
    }


    // Define the updateData method to update the dataset

}
