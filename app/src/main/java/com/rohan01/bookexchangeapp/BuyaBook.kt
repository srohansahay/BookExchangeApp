package com.rohan01.bookexchangeapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore


import kotlinx.android.synthetic.main.activity_buy_abook.*
import kotlinx.android.synthetic.main.list_item.*

class BuyaBook : AppCompatActivity() {


    private companion object {
        private val TAG = "BuyaBook"

    }


    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_abook)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.VERTICAL


        recyclerview.layoutManager = layoutManager


       val adapter = SellBookAdapter(this, BookNames.Supplier.booknames)
         recyclerview.adapter = adapter

        /*imgShare.setOnClickListener {

        }*/

    }

  /*  private fun showAlertDialog1(){

        val dialog = AlertDialog.Builder(this)
                .setTitle("Buy this book ${map01[${document.id}]}")
                .setView(R.layout.dialogbox)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("SELL", null)
                .show()

    }*/

}