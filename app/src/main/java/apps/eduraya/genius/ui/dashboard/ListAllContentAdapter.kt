package apps.eduraya.genius.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.genius.data.responses.DataAllContent
import apps.eduraya.genius.data.responses.DataClass
import apps.eduraya.genius.databinding.ItemListClassBinding

class ListAllContentAdapter (val items: ArrayList<DataAllContent>?):RecyclerView.Adapter<ListAllContentAdapter.ListAllContentViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListAllContentViewHolder(itemView: ItemListClassBinding): RecyclerView.ViewHolder(itemView.root) {
        var tvTitle: TextView
        var tvDesc: TextView
        var tvDesc2: TextView

        init {
            tvTitle = itemView.tvTitleClass
            tvDesc = itemView.tvDesc
            tvDesc2 = itemView.tvDesc2
        }

        fun bind(listAllContent: DataAllContent){
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(listAllContent)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(dataAllContent: DataAllContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAllContentViewHolder {
        val view = ItemListClassBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ListAllContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAllContentViewHolder, position: Int) {

        val listAllContent= items!![position]

        holder.bind(listAllContent)
        holder.tvTitle.text = listAllContent.title
        holder.tvDesc.text = listAllContent.course
        holder.tvDesc2.text = listAllContent.classroom
    }

    override fun getItemCount(): Int = items!!.size

    fun clear(){
        items?.clear()
        notifyDataSetChanged()
    }
}