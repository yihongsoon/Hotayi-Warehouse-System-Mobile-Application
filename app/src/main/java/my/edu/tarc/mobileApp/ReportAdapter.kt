package my.edu.tarc.mobileApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val serial : TextView = itemView.findViewById(R.id.tvSerial)
        val partNo : TextView = itemView.findViewById(R.id.tvPartNo)
        val status : TextView = itemView.findViewById(R.id.tvStatus)
    }
}