import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.websiteTextView.text = university.website

        // Set an onClick listener for the website TextView
        holder.websiteTextView.setOnClickListener {
            onItemClick(university.website) // Pass the website URL to the click listener
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
