package com.example.pickfresh.admin

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import coil.load
import com.example.pickfresh.R
import com.example.pickfresh.databinding.RentalLayoutBinding
import com.example.pickfresh.responses.Retrofit
import com.example.pickfresh.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class AddRental : AppCompatActivity() {
    private lateinit var bind: RentalLayoutBinding
    var encoded = ""

    @SuppressLint("MissingPermission", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = RentalLayoutBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val dialog = Dialog(this).apply {
            setContentView(R.layout.progressdi)
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val image = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null) {
                val bitmap = it.data!!.extras!!.get("data") as Bitmap
                val out = ByteArrayOutputStream()
                Toast.makeText(this, "$bitmap", Toast.LENGTH_SHORT).show()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                encoded = Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP)
                val imageview = bind.imageView25
                imageview.load(bitmap)
                imageview.scaleType = ImageView.ScaleType.CENTER_CROP
                imageview.setPadding(5)

            }
        }

        bind.cardview.setOnClickListener {
            image.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }



        bind.btnadd.setOnClickListener { tt ->
            val rentalType = bind.rentalType.text.toString().trim()
            val description = bind.rentalD.text.toString().trim()
            val rentalHours = bind.rentalHours.text.toString().trim()
            val rentalMobile = bind.rentalMobile.text.toString().trim()

            if (rentalType.isEmpty()) {
                tt.toast("Please Enter Seller name")
            } else if (description.isEmpty()) {
                tt.toast("Please Enter Seller email")
            } else if (rentalHours.isEmpty()) {
                tt.toast("Please Enter Seller password")
            } else if (rentalMobile.isEmpty()) {
                tt.toast("Please Enter Seller Mobile number")
            } else if (rentalMobile.length != 10) {
                tt.toast("Please Enter Valid number of Seller")
            }else{
                dialog.show()
                CoroutineScope(IO).async {
                    async {
                        try {
                            Retrofit.instance.addRental(
                                imageurl = encoded,
                                rentalType = rentalType,
                                rentalRupees = rentalHours,
                                dateOn = "${System.currentTimeMillis()}",
                                rentalDes = description,
                                rentalMobile=rentalMobile
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }.await().let {
                        withContext(Main) {
                            val message = it?.body()?.message?.let {
                                if (it == "Success") {
                                    finish()
                                }
                                it
                            }

                            Toast.makeText(applicationContext, "$message", Toast.LENGTH_SHORT)
                                .show()
                            dialog.dismiss()
                        }
                    }
                }.start()

            }
        }

    }


}