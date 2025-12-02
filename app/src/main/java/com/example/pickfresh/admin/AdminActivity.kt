package com.example.pickfresh.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickfresh.MainActivity
import com.example.pickfresh.R
import com.example.pickfresh.responses.Retrofit
import com.example.pickfresh.seller.addOns.ViewMyOrders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AdminActivity : AppCompatActivity() {
    lateinit var cycle: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        findViewById<FloatingActionButton>(R.id.add).setOnClickListener {
            startActivity(Intent(this, AddRental::class.java))
        }
        supportActionBar.apply {
            title = "hi Admin!!"
        }
        findViewById<FloatingActionButton>(R.id.viewOrders).setOnClickListener {
            startActivity(Intent(applicationContext,ViewMyOrders::class.java).apply {
                putExtra("type","viewPartOfUserOrders")
            })
        }
        findViewById<FloatingActionButton>(R.id.logout).setOnClickListener {
            dialog()
        }
        findViewById<FloatingActionButton>(R.id.addFertilizers).setOnClickListener {
            startActivity(
                Intent(applicationContext, AddProducts::class.java)
            )
        }
        findViewById<FloatingActionButton>(R.id.governmentSchem).setOnClickListener {
            startActivity(
                Intent(applicationContext, GovernmentScheme::class.java)
            )
        }
    }

    private fun dialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Do you want Logout ?")
            setMessage("Press \"Yes\" to logout or \"No\" Cancel !!")
            setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                finishAffinity()
                startActivity(Intent(this@AdminActivity, MainActivity::class.java))
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    override fun onStart() {
        super.onStart()
        cycle = findViewById(R.id.recyclerView)

        cycle.layoutManager = LinearLayoutManager(this)

        data()

    }

    override fun onResume() {
        super.onResume()
        data()
    }

    private fun data() {
        CoroutineScope(IO).async {
            async {
                try {
                    Retrofit.instance.getRental(
                        condition = "getRentalVehicles"
                    )
                } catch (e: Exception) {
                    null
                }
            }.await().let {
                it?.body()?.data?.let {
                    withContext(Main) {
                        cycle.adapter = AdapterForSellerList(applicationContext, it)
                    }
                }
            }
        }.start()
    }
}