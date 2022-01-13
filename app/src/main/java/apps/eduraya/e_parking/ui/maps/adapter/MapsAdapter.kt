package apps.eduraya.e_parking.ui.maps.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import apps.eduraya.e_parking.data.responses.ListDataPlace
import apps.eduraya.e_parking.databinding.ListItemLocationBinding
import apps.eduraya.e_parking.ui.valet.ChooseVehicleActivity

class MapsAdapter(private val context: Context) : RecyclerView.Adapter<MapsAdapter.MapsViewHolder>() {

    private val placeResultArrayList = ArrayList<ListDataPlace?>()

    fun setLocationAdapter(itemsPlace: ArrayList<ListDataPlace?>?) {
        placeResultArrayList.clear()
        placeResultArrayList.addAll(itemsPlace!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsViewHolder {
        val view = ListItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        val placeResult = placeResultArrayList[position]

        holder.tvSpaceMobilReguler.isVisible = false
        holder.tvSpaceMobilValet.isVisible = false
        holder.tvSpaceMotorValet.isVisible = false
        holder.tvSpaceMotorReguler.isVisible = false

        holder.tvNamaJalan.text = placeResult?.address
        holder.tvNamaLokasi.text = placeResult?.name
        placeResult?.quotas?.forEach {
            if(it?.vehicleId == 1){
                holder.tvSpaceMotorValet.isVisible = true
                holder.tvSpaceMotorReguler.isVisible = true

                holder.tvSpaceMotorValet.text = "Motor: " + it.quotaValet.toString()
                holder.tvSpaceMotorReguler.text = "Motor: " + it.quotaRegular.toString()
            }
            if(it?.vehicleId == 2) {
                holder.tvSpaceMobilReguler.isVisible = true
                holder.tvSpaceMobilValet.isVisible = true

                holder.tvSpaceMobilReguler.text = "Mobil: " + it.quotaRegular.toString()
                holder.tvSpaceMobilValet.text = "Mobil: " + it.quotaValet.toString()
            }
        }

        //set data to share & intent
        val strNamaLokasi = placeResultArrayList[position]?.name
        val strNamaJalan = placeResultArrayList[position]?.address
        val strIdPlace = placeResultArrayList[position]?.id
        var strTotalMotor = "0"
        var strTotalMobil = "0"

        //send data to another activity
        holder.actionValet.setOnClickListener {
            val intent = Intent(context, ChooseVehicleActivity::class.java)
            intent.putExtra("KEY_ID_PLACE", strIdPlace.toString())
            intent.putExtra("KEY_NAME_PLACE", strNamaLokasi.toString())
            intent.putExtra("KEY_ADDRESS_PLACE", strNamaJalan)
            placeResult?.quotas?.forEach {
                if (it?.vehicleId == 1){
                    strTotalMotor = it.quotaValet.toString()
                    intent.putExtra("KEY_TOTAL_MOTOR", strTotalMotor)
                }
                if (it?.vehicleId == 2){
                    strTotalMobil = it.quotaValet.toString()
                    intent.putExtra("KEY_TOTAL_CAR", strTotalMobil)
                }
            }

            context.startActivity(intent)
        }

        //intent to share location
//        holder.imageShare.setOnClickListener {
//            val strUri = "http://maps.google.com/maps?saddr=$strLat,$strLong"
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "text/plain"
//            intent.putExtra(Intent.EXTRA_SUBJECT, strNamaLokasi)
//            intent.putExtra(Intent.EXTRA_TEXT, strUri)
//            context.startActivity(Intent.createChooser(intent, "Bagikan :"))
//        }
    }

    override fun getItemCount(): Int {
        return placeResultArrayList.size
    }

    class MapsViewHolder(itemView: ListItemLocationBinding) : RecyclerView.ViewHolder(itemView.root) {
        var actionValet: LinearLayout
        var tvNamaJalan: TextView
        var tvNamaLokasi: TextView
        var tvSpaceMobilValet: TextView
        var tvSpaceMotorValet: TextView
        var tvSpaceMobilReguler: TextView
        var tvSpaceMotorReguler: TextView


        init {
            actionValet = itemView.actionValet
            tvNamaJalan = itemView.tvNamaJalan
            tvNamaLokasi = itemView.tvNamaLokasi
//            imageShare = itemView.imageShare
            tvSpaceMobilValet = itemView.tvSpaceMobilValet
            tvSpaceMotorValet = itemView.tvSpaceMotorValet
            tvSpaceMobilReguler = itemView.tvSpaceMobilReguler
            tvSpaceMotorReguler = itemView.tvSpaceMotorReguler
        }
    }

}