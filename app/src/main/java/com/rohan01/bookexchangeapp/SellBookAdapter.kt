package com.rohan01.bookexchangeapp

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.list_item.view.*

class SellBookAdapter(val context: Context, var booknames: MutableList<BookNames>): RecyclerView.Adapter<SellBookAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return booknames.size
    }


    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val hobby = booknames[position]
        holder.setData(hobby,position)
    }





    inner class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var currentBookNames: BookNames? = null
        var currentPosition: Int = 0



       /* init {
            itemView.setOnClickListener {
                Toast.makeText(context, currentBookNames!!.title + "Clicked!", Toast.LENGTH_SHORT).show()
                showAlertDialog1()
            }
            itemView.imgBuy.setOnClickListener {
                showAlertDialog1()

            }
        }*/

       /* private fun showAlertDialog1() {

            val dialog = AlertDialog.Builder(context)
                    .setTitle("Do you wish to buy the book ${currentBookNames!!.title}?")
                    .setView(R.layout.dialogbox02)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("SELL", null)
                    .show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
              //  Log.i(MainActivity.TAG, "Clicked on positive button!")
                Toast.makeText(context, "You purchased ${currentBookNames!!.title}", Toast.LENGTH_SHORT).show()

            }
            dialog.dismiss()
        }*/

            fun setData(hobby: BookNames?, pos: Int) {
                itemView.text1.text = hobby!!.title

                this.currentBookNames = hobby
                this.currentPosition = pos
            }




    }
}