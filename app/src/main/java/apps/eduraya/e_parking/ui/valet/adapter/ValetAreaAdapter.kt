package apps.eduraya.e_parking.ui.valet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.e_parking.data.responses.valet.DataValetArea
import apps.eduraya.e_parking.databinding.ListItemValetAreaBinding

class ValetAreaAdapter (val items: ArrayList<DataValetArea>):RecyclerView.Adapter<ValetAreaAdapter.ValetAreaViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ValetAreaViewHolder(itemView:ListItemValetAreaBinding): RecyclerView.ViewHolder(itemView.root) {
        var tvFloor : TextView
        var tvCode : TextView

        init {
            tvFloor = itemView.tvFloor
            tvCode = itemView.tvCode
        }

        fun bind(listValetArea: DataValetArea){
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(listValetArea)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(dataValetArea: DataValetArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValetAreaViewHolder {
        val view = ListItemValetAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ValetAreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ValetAreaViewHolder, position: Int) {

        val listValetArea = items[position]

        holder.bind(listValetArea)
        holder.tvFloor.text = "Lantai ${listValetArea.floor}"
        holder.tvCode.text = "Kode: ${listValetArea.codeArea}"
    }

    override fun getItemCount(): Int = items.size
}