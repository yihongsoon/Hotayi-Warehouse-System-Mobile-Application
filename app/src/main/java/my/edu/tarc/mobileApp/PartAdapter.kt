package my.edu.tarc.mobileApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PartAdapter internal constructor(): RecyclerView.Adapter<PartAdapter.ViewHolder>(){
    private var partList = emptyList<Part>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //To hold each record
        val txtSerial: TextView = view.findViewById(R.id.txtRecSerial)
        val txtPartNo: TextView = view.findViewById(R.id.txtRecPart)
        val txtQty: TextView = view.findViewById(R.id.txtRecQty)
        val txtStatus: TextView = view.findViewById(R.id.txtRecStatus)
        val txtReceiDate: TextView = view.findViewById(R.id.txtRecReceiDate)
        val txtRackID: TextView = view.findViewById(R.id.txtRecRackID)
        val txtRackNo: TextView = view.findViewById(R.id.txtRecRackNo)
        val txtRackIn: TextView = view.findViewById(R.id.txtRecRackIn)
        val txtRackOut: TextView = view.findViewById(R.id.txtRecRackOut)
        val txtReceiBy: TextView = view.findViewById(R.id.txtRecReceiBy)
        val txtStoreBy: TextView = view.findViewById(R.id.txtRecStoreBy)
        val txtRetriBy: TextView = view.findViewById(R.id.txtRecRetriBy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //To create a layout to hold each record
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Connect data to the layout
        holder.txtSerial.text = partList[position].serial
        holder.txtPartNo.text = partList[position].part
        holder.txtQty.text = partList[position].qty
        holder.txtStatus.text = partList[position].status
        holder.txtReceiDate.text = partList[position].receivedate
        holder.txtRackID.text = partList[position].rackid
        holder.txtRackNo.text = partList[position].rackno
        holder.txtRackIn.text = partList[position].rackin
        holder.txtRackOut.text = partList[position].rackout
        holder.txtReceiBy.text = partList[position].staffid
        holder.txtStoreBy.text = partList[position].storeby
        holder.txtRetriBy.text = partList[position].retrieveby
        holder.itemView.setOnClickListener{
            Toast.makeText(it.context, "Serial:" + partList[position].serial, Toast.LENGTH_SHORT).show()
        }
    }

    internal fun setContact(part: List<Part>){
        this.partList = part
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}