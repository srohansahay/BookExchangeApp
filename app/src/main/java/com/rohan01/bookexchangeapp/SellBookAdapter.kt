package com.rohan01.bookexchangeapp

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.list_item.view.*

class SellBookAdapter(val context: Context, var booknames: MutableList<BookNames>, var booklinks: MutableList<BookLinks>): RecyclerView.Adapter<SellBookAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return booknames.size
    }


    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val hobby = booknames[position]
        val linked = booklinks[position]
        holder.setData(hobby,position, linked)
    }





    inner class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var currentBookNames: BookNames? = null
        var currentPosition: Int = 0
        var currentLinkNames: BookLinks? = null





        init {
            itemView.setOnClickListener {
                Toast.makeText(context, "Purchase "+currentBookNames!!.title , Toast.LENGTH_SHORT).show()


                 showAlertDialog1()
            }
            itemView.imgBuy.setOnClickListener {
                showAlertDialog1()
                Toast.makeText(context, "Purchase "+currentBookNames!!.title, Toast.LENGTH_SHORT).show()

            }
        }

        private fun showAlertDialog1() {

            val dialog = AlertDialog.Builder(context)
                    .setTitle("Do you wish to buy the book ${currentBookNames!!.title}?")
                    .setView(R.layout.dialogbox02)
                    .setPositiveButton("Buy", null)
                    .setNegativeButton("Cancel", null)
                    .show()




            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                //  Log.i(MainActivity.TAG, "Clicked on positive button!")
                Toast.makeText(context, "You purchased ${currentBookNames!!.title}", Toast.LENGTH_SHORT).show()

              showAlertDialog2()

                dialog.dismiss()
            }

        }

            fun setData(hobby: BookNames?, pos: Int, linked: BookLinks?) {
                itemView.text1.text = hobby!!.title

                this.currentBookNames = hobby
                this.currentPosition = pos
                this.currentLinkNames = linked
            }


        private fun showAlertDialog2() {

            val dialog = AlertDialog.Builder(context)
                    .setTitle(" ${currentLinkNames!!.link}?")
                    .setView(R.layout.dialogbox03)
                    .setPositiveButton("Download", null)
                    .setNegativeButton("Cancel", null)
                    .show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                Toast.makeText(context, "Use the above link  ${currentLinkNames!!.link} to download the book ", Toast.LENGTH_LONG).show()


                dialog.dismiss()

            }


        }


    }
}