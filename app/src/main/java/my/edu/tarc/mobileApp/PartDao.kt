package my.edu.tarc.mobileApp

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Insert
import androidx.room.Dao

@Dao
interface PartDao {

    @Insert
    suspend fun insert(part: Part)

    @Update
    suspend fun update(part: Part)

    @Query("SELECT * FROM part ORDER BY serial ASC")
    fun getAllPart(): LiveData<List<Part>>
}