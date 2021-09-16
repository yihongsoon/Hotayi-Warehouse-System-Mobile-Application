package my.edu.tarc.mobileApp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "part")
data class Part(@PrimaryKey val serial:String, val part:String, val qty:String, val status:String, val receivedate:String, val rackid:String,
                val rackno:String, val rackin:String,  val rackout:String,  val staffid:String, val storeby:String, val retrieveby:String) {

    /*override fun toString(): String {
        return "$serial :$part :$qty :$status :$receivedate :$rackid :$rackno :$rackin :$rackout :$staffid :$storeby :$retrieveby"
    }*/
}