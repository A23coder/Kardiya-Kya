package com.example.to_do_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.adapter.DataAdapter
import com.example.to_do_app.database.DatabaseClass
import com.example.to_do_app.database.To_Do_Data
import com.example.to_do_app.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseClass

    @SuppressLint("SetTextI18n" , "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = DatabaseClass.getDatabase(applicationContext)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.txtDate.text = Utils.sdf.format(Date())
        CoroutineScope(Dispatchers.Main).launch {
            database = withContext(
                Dispatchers.Main
            ) {
                DatabaseClass.getDatabase(applicationContext)
            }
            getData()
        }
        binding.btnAddData.setOnClickListener {
            AddData()
        }
        binding.btnAddNew.setOnClickListener {
            AddData()
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun getData() {
        val dao = database.toDoDao()
        val data_list: List<To_Do_Data> = withContext(Dispatchers.IO) {
            dao.getAllData()
        }

        recyclerView.adapter = DataAdapter(data_list , database , binding)
        val taskCount = withContext(Dispatchers.IO) {
            dao.totalTask()
        }
        withContext(Dispatchers.Main) {
            binding.txtTodayTask.text = "Total $taskCount Tasks"
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SimpleDateFormat" , "SetTextI18n" , "NotifyDataSetChanged")
    private fun AddData() {
        val builder = AlertDialog.Builder(this)
        val customLayout = layoutInflater.inflate(R.layout.activity_add_data , null)
        builder.setView(customLayout)

        val saveBtn = customLayout.findViewById<Button>(R.id.btn_save)
        val edtTitle = customLayout.findViewById<EditText>(R.id.edt_title)
        val edtDetail = customLayout.findViewById<EditText>(R.id.edt_details)
        val sdf = SimpleDateFormat("d-MMMM-yyyy")
        val txtGetDate = customLayout.findViewById<TextView>(R.id.txt_get_date)
        txtGetDate.text = sdf.format(Date())

        val dialog = builder.create()
        dialog.show()

        txtGetDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dateDialog = DatePickerDialog(this , { _ , year , month , dayOfMonth ->
                txtGetDate.text = "$dayOfMonth-${Utils.MONTHS[month]}-$year"
            } , year , month , day)
            dateDialog.show()
        }
        saveBtn.setOnClickListener {
            println("========${edtTitle.text} ${edtDetail.text}")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val data = To_Do_Data(
                        0 ,
                        edtTitle.text.toString() ,
                        edtDetail.text.toString() ,
                        txtGetDate.text.toString()
                    )
                    database.toDoDao().insertData(data)
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity , "Data Successfully Added" , Toast.LENGTH_SHORT
                        ).show()
                        getData()
                    }
                } catch (_: Exception) {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity ,
                            "Data is not added due to some issue" ,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            dialog.dismiss()
        }
    }
}
