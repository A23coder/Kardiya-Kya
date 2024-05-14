package com.example.to_do_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_table")
data class To_Do_Data(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    val title: String ,
    val detail: String ,
    val date: String ,
    var isCompleted: Boolean = false ,
)