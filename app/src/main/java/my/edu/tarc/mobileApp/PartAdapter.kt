package my.edu.tarc.mobileApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.record_layout.view.*

class PartAdapter: RecyclerView.Adapter<PartAdapter.ViewHolder>(){

    private var partList = emptyList<Part>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //To hold each record
        /*val txtSerial: TextView = view.findViewById(R.id.txtRecSerial)
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
        val txtRetriBy: TextView = view.findViewById(R.id.txtRecRetriBy)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //To create a layout to hold each record
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.record_layout, parent, false)
        //return ViewHolder(view)
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.record_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Connect data to the layout
        val currentItem = partList[position]
        holder.itemView.txtRecSerial.text = currentItem.serial.toString()
        holder.itemView.txtRecPart.text = currentItem.part.toString()
        holder.itemView.txtRecQty.text = currentItem.qty.toString()
        holder.itemView.txtRecStatus.text = currentItem.status.toString()
        holder.itemView.txtRecReceiDate.text = currentItem.receivedate.toString()
        holder.itemView.txtRecRackID.text = currentItem.rackid.toString()
        holder.itemView.txtRecRackNo.text = currentItem.rackno.toString()
        holder.itemView.txtRecRackIn.text = currentItem.rackin.toString()
        holder.itemView.txtRecRackOut.text = currentItem.rackout.toString()
        holder.itemView.txtRecReceiBy.text = currentItem.staffid.toString()
        holder.itemView.txtRecStoreBy.text = currentItem.storeby.toString()
        holder.itemView.txtRecRetriBy.text = currentItem.retrieveby.toString()
    }

    fun setData(part: List<Part>){
        this.partList = part
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return partList.size
    }

}