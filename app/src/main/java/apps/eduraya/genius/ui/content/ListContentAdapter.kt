package apps.eduraya.genius.ui.content

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.genius.data.responses.DataContent
import apps.eduraya.genius.databinding.ItemListClassBinding

class ListContentAdapter (val items: ArrayList<DataContent>?):RecyclerView.Adapter<ListContentAdapter.ListContentViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListContentViewHolder(itemView: ItemListClassBinding): RecyclerView.ViewHolder(itemView.root) {
        var tvTitle: TextView
        var tvDesc: TextView
        var tvDesc2: TextView

        init {
            tvTitle = itemView.tvTitleClass
            tvDesc = itemView.tvDesc
            tvDesc2 = itemView.tvDesc2
        }

        fun bind(listContent: DataContent){
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(listContent)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(dataContent: DataContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListContentViewHolder {
        val view = ItemListClassBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ListContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListContentViewHolder, position: Int) {

        val listContent= items!![position]

        holder.bind(listContent)
        holder.tvTitle.isVisible = false
        holder.tvDesc.text = listContent.title.toString()
        holder.tvDesc2.isVisible = false
    }

    override fun getItemCount(): Int = items!!.size

}