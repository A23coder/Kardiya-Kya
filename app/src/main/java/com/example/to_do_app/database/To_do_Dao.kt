package com.example.to_do_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface To_do_Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: To_Do_Data)

    @Query("select * from to_do_table")
    fun getAllData(): List<To_Do_Data>

    @Query("select count(title) from to_do_table")
    fun totalTask(): Int

    @Query("SELECT * FROM to_do_table WHERE id = :id")
    suspend fun getDataById(id: Int): To_Do_Data

    @Update
    suspend fun updateData(data: To_Do_Data)

    @Query("DELETE FROM to_do_table WHERE id = :id")
    suspend fun deleteDataById(id: Int)

    @Query("SELECT isCompleted FROM to_do_table WHERE id = :itemId")
    fun loadCheckBoxState(itemId: Int): Boolean

    @Query("UPDATE to_do_table SET isCompleted = :isChecked WHERE id = :itemId")
    fun saveCheckBoxState(itemId: Int, isChecked: Boolean)
}