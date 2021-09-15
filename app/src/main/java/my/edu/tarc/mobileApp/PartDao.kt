package my.edu.tarc.mobileApp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PartDao {

    @Insert
    suspend fun insert(part: Part)

    @Update
    suspend fun update(part: Part)

    @Query("SELECT * FROM material ORDER BY serial ASC")
    fun getAllContact(): LiveData<List<Part>>
}