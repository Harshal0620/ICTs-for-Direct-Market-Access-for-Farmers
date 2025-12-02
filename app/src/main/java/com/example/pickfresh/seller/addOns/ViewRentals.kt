package com.example.pickfresh.seller.addOns

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pickfresh.R
import com.example.pickfresh.admin.AdapterForSellerList
import com.example.pickfresh.databinding.ViewRentalsBinding
import com.example.pickfresh.responses.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ViewRentals : AppCompatActivity() {
    private val bind by lazy {
        ViewRentalsBinding.inflate(layoutInflater)
    }
    private val dialog by lazy {
        Dialog(this).apply {
            setContentView(R.layout.progressdi)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        dialog.show()
        CoroutineScope(IO).async {
            async {
                try {
                    Retrofit.instance.getRental("getRentalVehicles")
                } catch (e: Exception) {
                    null
                }
            }.await().let {
                withContext(Main) {
                    it?.body()?.data?.let {
                        bind.cycle6.adapter = AdapterForSellerList(
                            this@ViewRentals, it
                        )
                    }
                    dialog.dismiss()
                }
            }
        }.start()


    }
}