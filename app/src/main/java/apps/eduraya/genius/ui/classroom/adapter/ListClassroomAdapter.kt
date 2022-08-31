package apps.eduraya.genius.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.genius.data.responses.DataClass
import apps.eduraya.genius.databinding.ItemListClassBinding

class ListClassroomAdapter (val items: ArrayList<DataClass>?):RecyclerView.Adapter<ListClassroomAdapter.ListClassroomViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListClassroomViewHolder(itemView: ItemListClassBinding): RecyclerView.ViewHolder(itemView.root) {
        var tvTitle: TextView
        var tvDesc: TextView
        var tvDesc2: TextView

        init {
            tvTitle = itemView.tvTitleClass
            tvDesc = itemView.tvDesc
            tvDesc2 = itemView.tvDesc2
        }

        fun bind(listClassroom: DataClass){
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(listClassroom)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(dataClassroom: DataClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListClassroomViewHolder {
        val view = ItemListClassBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ListClassroomViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListClassroomViewHolder, position: Int) {

        val listClassroom= items!![position]

        holder.bind(listClassroom)
        holder.tvTitle.text = listClassroom.name
        holder.tvDesc.text = listClassroom.educational_level
        holder.tvDesc2.text = "Kelas "+listClassroom.name + " jenjang "+listClassroom.educational_level
    }

    override fun getItemCount(): Int = items!!.size
}