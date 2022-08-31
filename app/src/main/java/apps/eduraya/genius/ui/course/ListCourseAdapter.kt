package apps.eduraya.genius.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.genius.data.responses.DataCourse
import apps.eduraya.genius.databinding.ItemListClassBinding

class ListCourseAdapter (val items: ArrayList<DataCourse>?):RecyclerView.Adapter<ListCourseAdapter.ListCourseViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListCourseViewHolder(itemView: ItemListClassBinding): RecyclerView.ViewHolder(itemView.root) {
        var tvTitle: TextView
        var tvDesc: TextView
        var tvDesc2: TextView

        init {
            tvTitle = itemView.tvTitleClass
            tvDesc = itemView.tvDesc
            tvDesc2 = itemView.tvDesc2
        }

        fun bind(listCourse: DataCourse){
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(listCourse)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(dataCourse: DataCourse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCourseViewHolder {
        val view = ItemListClassBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ListCourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCourseViewHolder, position: Int) {

        val listCourse= items!![position]

        holder.bind(listCourse)
        holder.tvTitle.text = listCourse.name
        holder.tvDesc.text = "Mata Pelajaran ${listCourse.name}"
        holder.tvDesc2.isVisible = false
    }

    override fun getItemCount(): Int = items!!.size
}