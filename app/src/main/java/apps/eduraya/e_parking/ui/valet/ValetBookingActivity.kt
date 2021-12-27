package apps.eduraya.e_parking.ui.valet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import apps.eduraya.e_parking.R
import com.google.android.gms.maps.model.LatLng

class ValetBookingActivity : AppCompatActivity() {
    lateinit var strPlaceId: String
    lateinit var strNamaLokasi: String
    lateinit var strNamaJalan: String
    lateinit var strRating: String
    lateinit var strPhone: String
    lateinit var fromLatLng: LatLng
    lateinit var toLatLng: LatLng
    var strCurrentLatitude = 0.0
    var strLatitude = 0.0
    var strCurrentLongitude = 0.0
    var strLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valet_booking)

        //get data intent from adapter
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            strPlaceId = bundle["placeId"] as String
            strLatitude = bundle["lat"] as Double
            strLongitude = bundle["lng"] as Double
            strNamaJalan = bundle["vicinity"] as String

            //latlong origin & destination
            fromLatLng = LatLng(strCurrentLatitude, strCurrentLongitude)
            toLatLng = LatLng(strLatitude, strLongitude)

        }
    }
}