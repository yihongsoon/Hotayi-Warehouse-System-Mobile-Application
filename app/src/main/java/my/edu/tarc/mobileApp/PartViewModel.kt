package my.edu.tarc.mobileApp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PartViewModel(application: Application): AndroidViewModel(application){

    val getAllPart: LiveData<List<Part>>
    private val repository: PartRepository

    init {
        val partDao = PartDatabase.getDatabase(application).partDao()
        repository = PartRepository(partDao)
        getAllPart = repository.getAllPart
    }

    fun addPart(part: Part) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(part)

    }
}