package com.example.pickfresh.seller.addOns

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pickfresh.R
import com.example.pickfresh.databinding.DialogPointBinding
import com.example.pickfresh.databinding.ViewFertiBinding
import com.example.pickfresh.responses.Retrofit
import com.example.pickfresh.seller.addOns.adapters.AdapterForProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ViewFertilizers : AppCompatActivity() {
    private val bind by lazy {
        ViewFertiBinding.inflate(layoutInflater)
    }
    private val dialog by lazy {
        Dialog(this).apply {
            setContentView(R.layout.progressdi)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }
    }

    private val bindDialog by lazy {
        DialogPointBinding.inflate(layoutInflater)
    }
    private val dialogPoint by lazy {
        Dialog(this).apply {
            setContentView(bindDialog.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    var itemId = "Nothing"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        dialog.show()
        CoroutineScope(IO).async {
            async {
                try {
                    Retrofit.instance.getProducts(condition = "getFertiProducts")
                } catch (e: Exception) {
                    null
                }
            }.await().let {
                withContext(Main) {
                    it?.body()?.data?.let {
                        AdapterForProduct(applicationContext, it) { click ->
                            dialogPoint.show()
                            itemId = click.id ?: "Nothing"
                        }.also { bind.cycle7.adapter = it }
                    }
                    dialog.dismiss()
                }
            }
        }.start()

        bindDialog.placeOrder.setOnClickListener {
            val qty = bindDialog.qtyForFertilizer.text.toString().trim()
            if (qty.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Please enter the Purchase Quantity",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                dialog.show()
                CoroutineScope(IO).async {
                    async {
                        try {
                            Retrofit.instance.addPurchase(
                                itemId = itemId,
                                userId = getSharedPreferences("user", MODE_PRIVATE).getString(
                                    "id", ""
                                ) ?: "",
                                status = "Pending", qty = qty,
                                dateOn = "${System.currentTimeMillis()}"
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }.await().let {
                        withContext(Main) {
                            it?.body()?.message?.let {
                                if ("success" == it) {
                                    dialogPoint.dismiss()
                                    finish()
                                }
                                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                            }
                            dialog.dismiss()
                        }
                    }
                }.start()
            }


        }

    }
}