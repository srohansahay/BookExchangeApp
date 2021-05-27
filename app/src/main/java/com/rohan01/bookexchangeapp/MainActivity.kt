package com.rohan01.bookexchangeapp

import android.R.layout.*
import android.accounts.Account
import android.accounts.AccountManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.api.Distribution
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*


class MainActivity : AppCompatActivity() {


    data class Seller(
        val SellerName: String = "",
        val BookName: String = "",
        val Points: String = "",

        )

    class SellerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private companion object {
        val TAG = "MainActivity"


    }

    private lateinit var auth: FirebaseAuth

    var db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth


        val query = db.collection("SellOffer")

        val am: AccountManager = AccountManager.get(this) // "this" references the current Context

        val accounts: Array<out Account> = am.getAccountsByType("com.google")







        var options = FirestoreRecyclerOptions.Builder<Seller>().setQuery(query, Seller::class.java)
            .setLifecycleOwner(this).build()

        val adapter = object : FirestoreRecyclerAdapter<Seller, SellerViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {
                val view = LayoutInflater.from(this@MainActivity)
                    .inflate(simple_list_item_2, parent, false)

                return SellerViewHolder(view)
            }

            override fun onBindViewHolder(holder: SellerViewHolder, position: Int, model: Seller) {
                val tvBookName: TextView = holder.itemView.findViewById(android.R.id.text1)
                val tvPoints: TextView = holder.itemView.findViewById(android.R.id.text2)
                tvBookName.text = model.BookName
                tvPoints.text = model.Points


            }

        }

        rvSellers.layoutManager = LinearLayoutManager(this)
        rvSellers.adapter = adapter


    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {



        menuInflater.inflate(R.menu.menu_main, menu)
         return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miLogout) {
            Log.i(TAG, "Logout")
            auth.signOut()
            val logoutIntent = Intent(this, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
        }

       else if(item.itemId == R.id.mieditprofile){
            Log.i(TAG, "Edit profile")
            val editprofileintent = Intent(this, EditProfileActivity::class.java)

            startActivity(editprofileintent)
        }

        else if (item.itemId == R.id.miSellBook) {
             Log.i(TAG, "Show alert dialog to edit status")
            val sellbookintent = Intent(this, SellBook::class.java)
            startActivity(sellbookintent)

        }
        else if (item.itemId == R.id.miBuyaBook) {
            Log.i(TAG, "Show alert dialog to edit status")
            val buyabookintent = Intent(this, BuyaBook::class.java)
            startActivity(buyabookintent)

        }

        return super.onOptionsItemSelected(item)
    }


}