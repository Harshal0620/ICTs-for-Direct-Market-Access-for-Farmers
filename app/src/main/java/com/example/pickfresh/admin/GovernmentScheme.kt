package com.example.pickfresh.admin

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pickfresh.R
import com.example.pickfresh.databinding.ActivityGovernmentSchemeBinding
import com.example.pickfresh.responses.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GovernmentScheme : AppCompatActivity() {
    val dialog by lazy {
        Dialog(this).apply {
            setContentView(R.layout.progressdi)
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
    private val bind by lazy {
        ActivityGovernmentSchemeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        bind.btnAddScheme.setOnClickListener {
            val schemeDescription = bind.schemeDescription.text.toString().trim()
            val schemeUrl = bind.schemeUrl.text.toString().trim()

            CoroutineScope(IO).async {
                async {
                    try {
                        Retrofit.instance.addScheme(
                            url = schemeUrl,
                            schemeDes = schemeDescription,
                            dateUp = "${System.currentTimeMillis()}"
                        )
                    } catch (e: Exception) {
                        null
                    }
                }.await().let {
                    withContext(Main) {
                        if (it?.body()?.message == "Success") {
                            finish()
                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Failed to Add", Toast.LENGTH_SHORT)
                                .show()
                        }
                        dialog.dismiss()
                    }

                }
            }.start()

        }
    }
}