package my.edu.tarc.mobileApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ReportAdapter (private val reportList: ArrayList<ReportClass>):RecyclerView.Adapter<ReportAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.report_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReportAdapter.MyViewHolder, position: Int) {
        val currentItem = reportList[position]
        holder.serial.text = currentItem.serial
        holder.partNo.text = currentItem.part
        holder.status.text = currentItem.status
        holder.qty.text = currentItem.qty
        holder.rackid.text = currentItem.rackid
        holder.rackin.text = currentItem.rackin
        holder.rackout.text = currentItem.rackout
        holder.rackno.text = currentItem.rackno
        holder.receivedate.text = currentItem.receivedate
        holder.staffid.text = currentItem.staffid
        val isVisible : Boolean = currentItem.visibility
        holder.constraintLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

        holder.mainConstraintLayout.setOnClickListener{
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val serial : TextView = itemView.findViewById(R.id.tvSerial)
        val partNo : TextView = itemView.findViewById(R.id.tvPartNo)
        val status : TextView = itemView.findViewById(R.id.tvStatus)
        val qty : TextView = itemView.findViewById(R.id.detailQty)
        val rackid : TextView = itemView.findViewById(R.id.detailRackID)
        val rackin : TextView = itemView.findViewById(R.id.detailRackIn)
        val rackout : TextView = itemView.findViewById(R.id.detailRackOut)
        val rackno : TextView = itemView.findViewById(R.id.detailRackNo)
        val receivedate : TextView = itemView.findViewById(R.id.detailReceive)
        val staffid : TextView = itemView.findViewById(R.id.detailStaff)
        val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.expandedLayout)
        val mainConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.mainConstraintLayout)
    }
}