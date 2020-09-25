package com.example.recodesave.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar


fun Context.toast(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()}

fun AlertBox(context: FragmentActivity,title:String,msg:String) {
    val alertbox = AlertDialog.Builder(context)
    alertbox.setTitle(title)
    alertbox.setMessage(msg)
    alertbox.setPositiveButton(
        "Yes"
    ) { arg0, arg1 -> context.finish() }
    alertbox.setNegativeButton(
        "No"
    ) { arg0, arg1 -> }
    alertbox.show()
}
