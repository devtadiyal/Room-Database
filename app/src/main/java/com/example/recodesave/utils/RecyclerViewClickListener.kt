package com.example.recodesave.utils

import android.view.View
import com.example.recodesave.roomdb.User

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View,position:Int, user: User)
}