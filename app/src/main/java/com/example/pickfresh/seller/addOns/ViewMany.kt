package com.example.pickfresh.seller.addOns

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pickfresh.databinding.ViewManyBinding

class ViewMany : AppCompatActivity() {
    private val bind by lazy {
        ViewManyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        bind.sellersPoint.setOnClickListener {
            startActivity(
                Intent(this, ViewFertilizers::class.java)
            )
        }
        bind.requestsFrom.setOnClickListener {
            startActivity(
                Intent(this, ViewMyOrders::class.java)
            )
        }


    }
}