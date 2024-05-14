package com.example.to_do_app.adapter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.R
import com.example.to_do_app.Utils
import com.example.to_do_app.database.DatabaseClass
import com.example.to_do_app.database.To_Do_Data
import com.example.to_do_app.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class DataAdapter(
    private var data: List<To_Do_Data> ,
    private val database: DatabaseClass ,
    private val binding: ActivityMainBinding ,
) : RecyclerView.Adapter<DataAdapter.MyViewViewHolder>() {
    class MyViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txt_title)
        val detail: TextView = itemView.findViewById(R.id.txt_details)
        val date: TextView = itemView.findViewById(R.id.txt_date)
        val chBox: CheckBox = itemView.findViewById(R.id.chBox)
    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): MyViewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item , parent , false)
        return MyViewViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewViewHolder , position: Int) {
        val item = data[position]

        holder.title.text = item.title
        holder.detail.text = item.detail
        holder.date.text = item.date
        holder.chBox.isChecked = item.isCompleted

        if (item.isCompleted) {
            holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }


        holder.chBox.setOnCheckedChangeListener { _ , isChecked ->
            item.isCompleted = isChecked
            if (item.isCompleted) {
                item.isCompleted = true
                holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                item.isCompleted = false
                holder.title.paintFlags =
                    holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            CoroutineScope(Dispatchers.IO).launch {
                database.toDoDao().saveCheckBoxState(item.id , isChecked)
            }
            holder.title.invalidate()
        }

        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)

            val inflater =
                holder.itemView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val customLayout = inflater.inflate(
                R.layout.activity_add_update , null
            )
            builder.setView(customLayout)
            val dialog = builder.create()
            dialog.show()
            val updateBtn = customLayout.findViewById<Button>(R.id.btn_update)
            val deleteBtn = customLayout.findViewById<Button>(R.id.btn_delete)
            val edtTitle = customLayout.findViewById<TextView>(R.id.edt_title)
            val edtDate = customLayout.findViewById<TextView>(R.id.txt_get_date)
            val edtDetail = customLayout.findViewById<TextView>(R.id.edt_details)
            CoroutineScope(Dispatchers.Main).launch {
                val database = DatabaseClass.getDatabase(holder.itemView.context)
                val dataDao = database.toDoDao()
                val dataFromDatabase = dataDao.getDataById(item.id)

                edtTitle.text = dataFromDatabase.title
                edtDetail.text = dataFromDatabase.detail
                edtDate.text = dataFromDatabase.date
                edtDate.setOnClickListener {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    val dateDialog =
                        DatePickerDialog(it.context , { _ , year , month , dayOfMonth ->
                            edtDate.text = "$dayOfMonth-${Utils.MONTHS[month]}-$year"
                        } , year , month , day)
                    dateDialog.show()
                }
                updateBtn.setOnClickListener {
                    println("AAAAAAA${edtTitle.text} ${edtDetail.text} ${edtDate.text}")
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val updateData = To_Do_Data(
                                item.id ,
                                edtTitle.text.toString() ,
                                edtDetail.text.toString() ,
                                edtDate.text.toString()
                            )
                            dataDao.updateData(updateData)
                            Toast.makeText(
                                it.context , "Data Updated Successfully" , Toast.LENGTH_SHORT
                            ).show()
                            holder.title.text = edtTitle.text
                            holder.detail.text = edtDetail.text
                            holder.date.text = edtDate.text
                            dialog.dismiss()
                        } catch (e: Exception) {
                            Toast.makeText(
                                holder.itemView.context ,
                                "Failed to update data: ${e.message}" ,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                deleteBtn.setOnClickListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val database = DatabaseClass.getDatabase(holder.itemView.context)
                            val dataDao = database.toDoDao()
                            dataDao.deleteDataById(item.id)

                            val updatedList = data.toMutableList()
                            updatedList.remove(item)

                            data = updatedList
                            notifyItemRemoved(holder.adapterPosition)

                            updateTaskCount()
                            Toast.makeText(
                                holder.itemView.context ,
                                "Data Successfully Deleted" ,
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                        } catch (e: Exception) {
                            Toast.makeText(
                                holder.itemView.context ,
                                "Failed to delete data: ${e.message}" ,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateTaskCount() {
        val dao = database.toDoDao()
        val taskCount = withContext(Dispatchers.IO) {
            dao.totalTask()
        }
        withContext(Dispatchers.Main) {
            binding.txtTodayTask.text = "$taskCount Tasks"
        }

    }

}