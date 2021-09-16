package my.edu.tarc.mobileApp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class PartViewModel(application: Application): AndroidViewModel(application){

    var partList : LiveData<List<Part>>
    private val repository: PartRepository

    init {
        Log.d("ViewModel", "Initialize")
        val partDao = PartDatabase.getDatabase(application).partDao()
        repository = PartRepository(partDao)
        partList = repository.allPart
    }

    fun addPart(part: Part) = viewModelScope.launch{
        repository.insert(part)

    }
}