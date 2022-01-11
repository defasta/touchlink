package apps.eduraya.e_parking.ui.payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.e_parking.data.responses.deposit.DataDeposit
import apps.eduraya.e_parking.databinding.ListItemDepositBinding

class PaymentAdapter(val items: ArrayList<DataDeposit>) :RecyclerView.Adapter<PaymentAdapter.PaymentViewHoder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
//    private val listDepositArrayList = ArrayList<DataDeposit>()
//
//    fun setPaymentAdapter(items: ArrayList<DataDeposit>){
//        listDepositArrayList.clear()
//        listDepositArrayList.addAll(items)
//    }

    fun setOnItemClickCallback(onItemClickCallback: PaymentAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class PaymentViewHoder(itemView: ListItemDepositBinding): RecyclerView.ViewHolder(itemView.root) {
        var tvAmount: TextView
        var tvStatus: TextView

        init {
            tvAmount = itemView.tvAmount
            tvStatus = itemView.tvStatus
        }

        fun bind(listDeposit:DataDeposit){
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(listDeposit)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHoder {
        val view = ListItemDepositBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentViewHoder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHoder, position: Int) {
        val listDeposit = items[position]

        holder.bind(listDeposit)
        holder.tvAmount.text = listDeposit.amount.toString()
        holder.tvStatus.text = listDeposit.status

    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickCallback{
        fun onItemClicked(dataDeposit:DataDeposit)
    }
}