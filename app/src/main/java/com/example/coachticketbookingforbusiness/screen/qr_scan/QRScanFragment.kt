package com.example.coachticketbookingforbusiness.screen.qr_scan

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_q_r_scan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QRScanFragment : BaseFragment(), ZXingScannerView.ResultHandler {

    companion object {
        @JvmStatic
        fun newInstance() = QRScanFragment()
        const val MY_PERMISSIONS_REQUEST_CAMERA = 1234
    }

    private var mOnReceiveQRCode: ((id: String) -> Unit)? = null

    fun setOnReceiveTicketId(listener: (id: String) -> Unit) {
        mOnReceiveQRCode = listener
    }

    override fun getLayoutId(): Int = R.layout.fragment_q_r_scan

    override fun initView() {
    }

    override fun initViewModel() {
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun observerForever() {
    }

    override fun observerOnce() {
    }

    override fun initListener() {

    }


    override fun onResume() {
        super.onResume()
        rootActivity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                scannerView.setResultHandler(this)
                scannerView?.startCamera()
            } else {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.CAMERA),
                    MY_PERMISSIONS_REQUEST_CAMERA
                );
            }
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scannerView.setResultHandler(this)
                scannerView?.startCamera()
            } else {
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun handleResult(rawResult: Result?) {
        if (rawResult != null) {
            mOnReceiveQRCode?.invoke(rawResult.text)
            popBackStack()
        } else {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(
                Runnable { scannerView?.resumeCameraPreview(this) },
                2000
            )
        }
    }

}