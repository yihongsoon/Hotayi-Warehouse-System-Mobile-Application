package my.edu.tarc.mobileApp

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class PartRepository (private val contactDao: PartDao){
    //A cache copy of data from Room
    val allContact: LiveData<List<Part>> = contactDao.getAllContact()

    //@Suppress("RedundantSuspentModifier")

    /*@WorkerThread
    suspend fun insert(part: Part){
        contactDao.insert(material)
    }

    @WorkerThread
    suspend fun delete(part: Part){
        contactDao.delete(material)
    }*/

}