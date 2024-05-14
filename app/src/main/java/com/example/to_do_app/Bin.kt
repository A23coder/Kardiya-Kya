package com.example.to_do_app

class Bin {
//    holder.itemView.setOnClickListener {
//            val inflater =
//                holder.itemView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val popUpWindow = inflater.inflate(R.layout.updatepopup , null)
//            val width = ViewGroup.LayoutParams.FILL_PARENT
//            val height = ViewGroup.LayoutParams.WRAP_CONTENT
//            val focusable = true
//            val popupWindow = PopupWindow(popUpWindow , width , height , focusable)
//            CoroutineScope(Dispatchers.Main).launch {
//                val database = DatabaseClass.getDatabase(holder.itemView.context)
//                val dataDao = database.DataDao()
//                val dataFromDatabase = dataDao.getDataById(item.id)
//
//                val accountNameText = popUpWindow.findViewById<TextView>(R.id.account_name)
//                val userNameText = popUpWindow.findViewById<TextView>(R.id.edt_username)
//                val passwordText = popUpWindow.findViewById<TextView>(R.id.etPassword)
//                val btnUpdate = popUpWindow.findViewById<Button>(R.id.update_btn)
//                val btnDelete = popUpWindow.findViewById<Button>(R.id.delete_btn)
//
//                val dec_password = dataFromDatabase.password
//
//                accountNameText.text = dataFromDatabase.account_name
//                userNameText.text = dataFromDatabase.username
//                passwordText.text = decryption(dec_password)
//
//                btnUpdate.setOnClickListener {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        try {
//                            val updatedData = DataClass(
//                                item.id ,
//                                accountNameText.text.toString() ,
//                                userNameText.text.toString() ,
//                                passwordText.text.toString()
//                            )
//                            dataDao.updateData(updatedData)
//                            Toast.makeText(
//                                holder.itemView.context ,
//                                "Data Successfully Updated" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            holder.account_name.text = accountNameText.text
//                            accountNameText.text = ""
//                            userNameText.text = ""
//                            passwordText.text = ""
//                        } catch (e: Exception) {
//                            Toast.makeText(
//                                holder.itemView.context ,
//                                "Failed to update data: ${e.message}" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//
//                btnDelete.setOnClickListener {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        try {
//                            val database = DatabaseClass.getDatabase(holder.itemView.context)
//                            val dataDao = database.DataDao()
//
//                            dataDao.deleteDataById(item.id)
//                            val updatedList = dataTransaction.toMutableList()
//                            updatedList.remove(item)
//                            dataTransaction = updatedList
//                            notifyItemRemoved(holder.adapterPosition)
//                            Toast.makeText(
//                                holder.itemView.context ,
//                                "Data Successfully Deleted" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            accountNameText.text = ""
//                            userNameText.text = ""
//                            passwordText.text = ""
//                        } catch (e: Exception) {
//                            Toast.makeText(
//                                holder.itemView.context ,
//                                "Failed to delete data: ${e.message}" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//                popupWindow.showAtLocation(popUpWindow , Gravity.BOTTOM , 0 , 100)
//            }
//            holder.account_name.text = item.account_name
//        }


//    9f66e9
}