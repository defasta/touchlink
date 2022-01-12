package apps.eduraya.e_parking.ui.my_qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.databinding.ActivityMyQrBinding
import apps.eduraya.e_parking.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.graphics.Bitmap
import android.graphics.Point
import android.view.Display

import androidmads.library.qrgenearator.QRGContents
import android.view.WindowManager

@AndroidEntryPoint
class MyQrActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityMyQrBinding
    private lateinit var qrgEncoder :QRGEncoder
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyQrBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val manager = getSystemService(WINDOW_SERVICE) as WindowManager

        // initializing a variable for default display.
        val display: Display = manager.defaultDisplay

        // creating a variable for point which
        // is to be displayed in QR Code.
        val point = Point()
        display.getSize(point)

        // getting width and
        // height of a point
        val width: Int = point.x
        val height: Int = point.y

        // generating dimension from width and height.
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        viewModel.getUserInfoDBObserver().observe(this,object : Observer<List<UserInfo>> {
            override fun onChanged(t: List<UserInfo>?) {
                t?.forEach {
                    qrgEncoder = QRGEncoder(it.id.toString(), null, QRGContents.Type.TEXT, dimen)
                    bitmap = qrgEncoder.encodeAsBitmap()
                    binding.ivMyqr.setImageBitmap(bitmap)
                }
            }

        })
    }
}