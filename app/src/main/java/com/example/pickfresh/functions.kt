package com.example.pickfresh

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


fun View.toast(message:Any){  Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show() }

@SuppressLint("ResourceAsColor")
fun View.snackBar(message: Any){
    Snackbar.make(this,"$message",Snackbar.LENGTH_SHORT).apply {
        setAction("Action") {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
        show()
    }

}
/*Connect your mobile ok its done*/

