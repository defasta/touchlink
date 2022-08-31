package apps.eduraya.genius.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.genius.data.responses.DataClass
import apps.eduraya.genius.databinding.ItemListClassBinding
import apps.eduraya.genius.databinding.ItemListClassHorizontalBinding

class ListHorizontalClassroomAdapter (val items: ArrayList<DataClass>?):RecyclerView.Adapter<ListHorizontalClassroomAdapter.ListHorizontalClassroomViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListHorizontalClassroomViewHolder(itemView: ItemListClassHorizontalBinding): RecyclerView.ViewHolder(itemView.root) {
        var tvTitle: TextView

        init {
            tvTitle = itemView.tvTitleClass
        }

        fun bind(listHorizontalClassroom: DataClass){
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(listHorizontalClassroom)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(dataHorizontalClassroom: DataClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHorizontalClassroomViewHolder {
        val view = ItemListClassHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ListHorizontalClassroomViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListHorizontalClassroomViewHolder, position: Int) {

        val listHorizontalClassroom= items!![position]

        holder.bind(listHorizontalClassroom)
        holder.tvTitle.text = listHorizontalClassroom.name
    }

    override fun getItemCount(): Int = items!!.size
}