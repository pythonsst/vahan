// File: UniversityViewModel.kt
import androidx.lifecycle.ViewModel
import com.task.vahan.University
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UniversityViewModel : ViewModel() {
    private val apiService = RetrofitClient.universityApiService

    suspend fun getUniversities(): List<University> = withContext(Dispatchers.IO) {
        try {
            apiService.getUniversities()
        } catch (e: Exception) {
            // Handle network errors
//            emptyList()
        } as List<University>
    }
}
