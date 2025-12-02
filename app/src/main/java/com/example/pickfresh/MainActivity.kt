package com.example.pickfresh

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pickfresh.admin.AdminActivity
import com.example.pickfresh.buyer.BuyerMainActivity
import com.example.pickfresh.seller.SellerMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val type=getSharedPreferences("user", MODE_PRIVATE).getString("type","")!!

        val array= arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.SEND_SMS)
        if(ActivityCompat.checkSelfPermission(this,array[0])!=PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(this,array[1])!=PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(this,array[2])!=PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(this,array[3])!=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(array,100)
            }else{
                ActivityCompat.requestPermissions(this,array,1000)
            }
        }
        findViewById<ImageView>(R.id.imageview).apply {
            alpha=0f

            animate().setDuration(500).alpha(1f).withEndAction {
                finish()
                when (type) {
                    "admin" -> {
                        startActivity(Intent(this@MainActivity,AdminActivity::class.java))
                    }
                    "seller" -> {
                        startActivity(Intent(this@MainActivity, SellerMainActivity::class.java))
                    }
                    "user" -> {
                        startActivity(Intent(this@MainActivity,BuyerMainActivity::class.java))
                    }
                    else -> {
                        startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                    }
                }
            }.withStartAction {
                overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in,
                    androidx.appcompat.R.anim.abc_fade_out)
            }
        }
    }
}