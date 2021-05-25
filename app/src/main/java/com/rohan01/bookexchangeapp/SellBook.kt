package com.rohan01.bookexchangeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


class SellBook: AppCompatActivity() {



    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialogbox)

        val store = findViewById<View>(R.id.btnBuyy) as Button

        store.setOnClickListener {
            view: View? -> store()
        }

    }

    private fun store()
    {
       val nameofbook = findViewById<View>(R.id.editTextBookName) as EditText
        val pointsofbook =  findViewById<View>(R.id.editTextBookPoints) as EditText
        val sellerofbook = findViewById<View>(R.id.editTextSellerName) as EditText
        val bookname = nameofbook.text.toString().trim()
        val bookpoints = pointsofbook.text.toString().trim()
        val sellername = sellerofbook.text.toString().trim()

        if(!bookname.isEmpty() && !bookpoints.isEmpty()  ){
           try {
               val items = HashMap<String, Any>()
               items.put("BookName",bookname)
               items.put("Points",bookpoints)
               items.put("SellerName",sellername)
               db.collection("SellOffer").document().set(items).addOnSuccessListener {
                  void:  Void? -> Toast.makeText(this,"Successfully updated to firestore",Toast.LENGTH_SHORT).show()
               }.addOnFailureListener { exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()  }
           }catch (e: Exception) {
               Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
           }
        }else{
            Toast.makeText(this,"Please fill up all the fields :(",Toast.LENGTH_SHORT).show()
        }

        val backtomainactivityintent = Intent(this, MainActivity::class.java)
        startActivity(backtomainactivityintent)

    }



}
