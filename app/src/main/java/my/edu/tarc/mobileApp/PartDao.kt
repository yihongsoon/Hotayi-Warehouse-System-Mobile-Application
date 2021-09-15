package my.edu.tarc.mobileApp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PartDao {
    @Query("SELECT * FROM material ORDER BY serial ASC")
    fun getAllContact(): LiveData<List<Part>>
}