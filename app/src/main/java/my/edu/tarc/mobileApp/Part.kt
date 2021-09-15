package my.edu.tarc.mobileApp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "material")
data class Part(@PrimaryKey val serial:String, val part:String, val qty:String, val rackid:String, val rackin:String,
                val rackno:String, val rackout:String, val receivedate:String, val staffid:String, val status:String,
                val storeby:String, val retrieveby:String) {
    override fun toString(): String {
        return "$serial :$part :$qty :$rackid :$rackin :$rackno :$rackout :$receivedate :$staffid :$status :$storeby :$retrieveby"
    }
}