package com.example.pickfresh

import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pickfresh.databinding.ActivitySignupBinding
import com.example.pickfresh.models.Onewordchange
import com.example.pickfresh.responses.CommonReponse
import com.example.pickfresh.responses.Retrofit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class Signup : AppCompatActivity() {
    private lateinit var bind: ActivitySignupBinding

    private val realString = ArrayList<String>()

    private lateinit var dialog: Dialog
    private val permission = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    lateinit var view: Onewordchange
    private lateinit var fused: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(bind.root)
        dialog = Dialog(this).apply {
            setContentView(R.layout.progressdi)
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        view = ViewModelProvider(this)[Onewordchange::class.java]
        fused = LocationServices.getFusedLocationProviderClient(this)
        realString.add("${bind.signtitle.text}")
        realString.add("${bind.namett.text}")
        realString.add("${bind.name1.hint}")
        realString.add("${bind.emailti.text}")
        realString.add("${bind.email1.hint}")
        realString.add("${bind.passworti.text}")
        realString.add("${bind.passwor.hint}")
        realString.add("${bind.mobilenum.text}")
        realString.add("${bind.mobile.hint}")
        realString.add("${bind.register.text}")
        realString.add("${bind.already.text}")
        realString.add("${bind.login.text}")
        type(language = intent.getStringExtra("language")!!)

        bind.login.setOnClickListener {
            finish()
        }

        bind.register.setOnClickListener { it ->
            val name1 = bind.name1.text.toString()
            val email1 = bind.email1.text.toString()
            val passwor = bind.passwor.text.toString()
            val mobile = bind.mobile.text.toString()

            if (name1.isEmpty()) {
                it.toast("Please enter your Name")
            } else if (email1.isEmpty()) {
                it.toast("Please enter your Email")

            } else if (!email1.contains("@gmail.com")) {
                it.toast("Enter valid Mail")
            } else if (passwor.isEmpty()) {
                it.toast("Please enter your Password")
            } else if (mobile.isEmpty()) {
                it.toast("Please enter your Mobile")
            } else if (mobile.length != 10) {
                it.toast("Please give valid Mobile number")
            } else {
                dialog.show()
                if (ActivityCompat.checkSelfPermission(
                        this,
                        permission[0]
                    ) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                        this,
                        permission[1]
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permission, 100)
                    } else {
                        ActivityCompat.requestPermissions(this, permission, 102)
                    }
                } else {
                    fused.lastLocation.addOnSuccessListener {

                        if (it != null) {

                            val format = DecimalFormat("##.#######")
                            MainScope().launch {
                                Retrofit.instance.signup(
                                    name = name1,
                                    email = email1,
                                    mobile = mobile,
                                    password = passwor,
                                    location = "${format.format(it.latitude)},${format.format(it.longitude)}"
                                ).enqueue(object : Callback<CommonReponse> {
                                    override fun onResponse(
                                        call: Call<CommonReponse>,
                                        response: Response<CommonReponse>
                                    ) {
                                        dialog.dismiss()
                                        response.body()!!.apply {
                                            Toast.makeText(this@Signup, message, Toast.LENGTH_SHORT)
                                                .show()
                                            if (message == "success") {
                                                finish()
                                            }
                                        }


                                    }

                                    override fun onFailure(
                                        call: Call<CommonReponse>,
                                        t: Throwable
                                    ) {
                                        dialog.dismiss()
                                        Toast.makeText(
                                            this@Signup,
                                            "${t.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })

                            }
                        } else {
                            dialog.dismiss()
                        }
                    }.addOnFailureListener {
                        dialog.dismiss()
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                        .addOnCanceledListener {
                            dialog.dismiss()
                        }


                }
            }
        }


    }


    private fun type(language: String) {
        when (language) {
            "English" -> {
                translated(realString)
            }

            "Tamil" -> {
                translateModel(TranslateLanguage.TAMIL)
            }

            "Telugu" -> {
                translateModel(TranslateLanguage.TELUGU)
            }

            "Kannada" -> {
                translateModel(TranslateLanguage.KANNADA)
            }

            "Hindi" -> {
                translateModel(TranslateLanguage.HINDI)
            }
        }
    }

    private fun translateModel(language: String) {
        dialog.show()
        val option = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(language)
            .build()
        val download = DownloadConditions.Builder()
            .build()

        val translate = Translation.getClient(option)
        translate.downloadModelIfNeeded(download)
            .addOnSuccessListener {
                view.option(option, realString)
                view.observer().observe(this) {
                    if (it.size == realString.size) {
                        bind.signtitle.text = it[0]
                        bind.namett.text = it[1]
                        bind.name1.hint = it[2]
                        bind.emailti.text = it[3]
                        bind.email1.hint = it[4]
                        bind.passworti.text = it[5]
                        bind.passwor.hint = it[6]
                        bind.mobilenum.text = it[7]
                        bind.mobile.hint = it[8]
                        bind.register.text = it[9]
                        bind.already.text = it[10]
                        bind.login.text = it[11]
                    }
                }
                dialog.dismiss()
            }.addOnFailureListener {
                bind.root.toast("1-> ${it.message}")
                dialog.dismiss()
            }
    }

    private fun translated(it: ArrayList<String>) {
        if (it.size == realString.size) {
            bind.signtitle.text = it[0]
            bind.namett.text = it[1]
            bind.name1.hint = it[2]
            bind.emailti.text = it[3]
            bind.email1.hint = it[4]
            bind.passworti.text = it[5]
            bind.passwor.hint = it[6]
            bind.mobilenum.text = it[7]
            bind.mobile.hint = it[8]
            bind.register.text = it[9]
            bind.already.text = it[10]
            bind.login.text = it[11]
        }
    }

}