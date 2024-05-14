package com.example.to_do_app

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class Utils {
    companion object {
        val MONTHS = mutableListOf(
            "January" ,
            "February" ,
            "March" ,
            "April" ,
            "May" ,
            "June" ,
            "July" ,
            "August" ,
            "September" ,
            "October" ,
            "November" ,
            "December"
        )
        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("d-MMMM-yyyy")

    }
}