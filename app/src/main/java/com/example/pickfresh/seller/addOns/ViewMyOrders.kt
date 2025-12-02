package com.example.pickfresh.seller.addOns

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pickfresh.R
import com.example.pickfresh.databinding.MyOrdersBinding
import com.example.pickfresh.responses.Retrofit
import com.example.pickfresh.seller.addOns.adapters.AdapterForOrders
import com.example.pickfresh.seller.addOns.adapters.AdapterForUserOrder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ViewMyOrders : AppCompatActivity() {
    private val bind by lazy {
        MyOrdersBinding.inflate(layoutInflater)
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
        getData()

    }

    private fun getData() {
        dialog.show()
        val id = getSharedPreferences("user", MODE_PRIVATE).getString("id", "") ?: ""
        val jj = intent.getStringExtra("type") ?: "getMyProductsOfFerti"
        CoroutineScope(IO).async {
            if (jj == "getMyProductsOfFerti") {
                async {
                    try {
                        Retrofit.instance.getMyOrders(id = id, condition = jj)
                    } catch (e: Exception) {
                        null
                    }
                }.await().let {
                    withContext(Main) {
                        it?.body()?.data?.let {

                            bind.cycle8.adapter = AdapterForOrders(applicationContext, it) {

                            }


                        }
                        dialog.dismiss()
                    }

                }
            } else {
                async {
                    try {
                        Retrofit.instance.getUsersOrders(condition = jj)
                    } catch (e: Exception) {
                        null
                    }
                }.await().let {
                    withContext(Main) {
                        it?.body()?.data?.let {

                            bind.cycle8.adapter = AdapterForUserOrder(this@ViewMyOrders, it) {
                                MaterialAlertDialogBuilder(this@ViewMyOrders).apply {
                                    setTitle("Do you want to Update the Order of ${it.productID}")
                                    setPositiveButton("Accepted") { ff, _ ->
                                        ff.dismiss()
                                        statusUpdate("Completed", it.productID ?: "")
                                    }
                                    setNegativeButton("Cancelled") { ff, _ ->
                                        ff.dismiss()
                                        statusUpdate("Cancelled", it.productID ?: "")

                                    }
                                    show()
                                }
                            }


                        }
                        dialog.dismiss()
                    }

                }
            }
        }.start()
    }

    private fun statusUpdate(status: String, productID: String) {
        dialog.show()
        CoroutineScope(IO).async {
            async {
                try {
                    Retrofit.instance.updateOrderView(
                        id = productID,
                        state = status
                    )
                } catch (e: Exception) {
                    withContext(Main){
                        Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    null
                }
            }.await().let {
                withContext(Main) {
                    it?.body()?.message?.let {
                        if (it == "success") {
                            getData()
                        }
                    }
                    dialog.dismiss()
                }
            }
        }.start()
    }
}