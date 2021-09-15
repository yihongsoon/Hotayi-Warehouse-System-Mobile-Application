package my.edu.tarc.mobileApp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Part::class),version = 1, exportSchema = false)
abstract class PartDatabase: RoomDatabase() {
    abstract fun partDao(): PartDao

    companion object{
        @Volatile
        private var INSTANCE : PartDatabase? = null

        fun getDatabase(context: Context): PartDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PartDatabase::class.java,
                    "part_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}