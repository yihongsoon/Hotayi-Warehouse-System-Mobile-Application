package my.edu.tarc.mobileApp

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class PartRepository (private val partDao: PartDao){
    //A cache copy of data from Room
    val allPart: LiveData<List<Part>> = partDao.getAllContact()

    //@Suppress("RedundantSuspentModifier")

    /*@WorkerThread
    suspend fun insert(part: Part){
        partDao.insert(material)
    }

    @WorkerThread
    suspend fun delete(part: Part){
        partDao.delete(material)
    }*/

}