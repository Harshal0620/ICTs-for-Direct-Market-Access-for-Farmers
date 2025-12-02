package com.example.pickfresh.admin

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pickfresh.R
import com.example.pickfresh.databinding.AddProductsBinding
import com.example.pickfresh.responses.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class AddProducts : AppCompatActivity() {
    private val bind by lazy {
        AddProductsBinding.inflate(layoutInflater)
    }
    private val dialog by lazy {
        Dialog(this).apply {
            setContentView(
                R.layout
                    .progressdi)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }
    }
    private var bitmap: Bitmap? = null
    private val activity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it?.data?.data?.let {
            contentResolver.openInputStream(it)?.readBytes()?.let {
                bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                bind.imageView3.setImageBitmap(bitmap)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        bind.imageView3.setOnClickListener {
            activity.launch(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"))
        }
        bind.btnAddFertilizer.setOnClickListener {
            val namePoint = bind.namePoint.text.toString().trim()
            val fertilizerCost = bind.fertilizerCost.text.toString().trim()

            if (namePoint.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Please enter your the Product name", Toast.LENGTH_SHORT
                ).show()
            } else if (fertilizerCost.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Please enter your the Product Cost", Toast.LENGTH_SHORT
                ).show()
            } else if (bitmap == null) {
                Toast.makeText(
                    applicationContext, "Please select image from your Gallery", Toast.LENGTH_SHORT
                ).show()
            } else {
                bitmap?.let {
                    val out = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.PNG, 100, out)
                    CoroutineScope(IO).async {
                        async {
                            try {
                                Retrofit.instance.addProduct(
                                    itemName = namePoint,
                                    image = Base64.encodeToString(
                                        out.toByteArray(),
                                        Base64.DEFAULT
                                    ),
                                    price = fertilizerCost
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }.await().let {
                            withContext(Main) {
                                it?.body()?.message?.let {
                                    if (it == "Success") {
                                        finish()
                                    }
                                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT)
                                        .show()
                                }

                                dialog.dismiss()
                            }
                        }
                    }.start()
                }
            }
        }

    }

}