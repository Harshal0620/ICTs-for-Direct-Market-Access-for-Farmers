package com.example.pickfresh.seller

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pickfresh.R
import com.example.pickfresh.responses.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class Schemes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schemes)
        val dialog = Dialog(this).apply {
            setContentView(R.layout.progressdi)
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.show()
        CoroutineScope(IO).async {
            async {
                try {
                    Retrofit.instance.getMySuggestions()
                } catch (e: Exception) {
                    null
                }
            }.await().let {
                withContext(Main) {
                    it?.body()?.data?.let {
                        findViewById<RecyclerView>(R.id.cycle9).adapter =
                            AdapterForScheme(this@Schemes, it)
                    }
                    dialog.dismiss()
                }
            }
        }.start()
    }
}