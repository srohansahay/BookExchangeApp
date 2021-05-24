package com.rohan01.bookexchangeapp

import android.R.layout.simple_list_item_single_choice
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

    class SellerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private companion object {
        private val TAG = "MainActivity"

    }

    private lateinit var auth: FirebaseAuth

    var db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth


        val query = db.collection("SellOffer")
        var options = FirestoreRecyclerOptions.Builder<Seller>().setQuery(query, Seller::class.java)
                .setLifecycleOwner(this).build()

        val adapter = object: FirestoreRecyclerAdapter<Seller, SellerViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {
                val view = LayoutInflater.from(this@MainActivity).inflate(
                        android.R.layout.simple_list_item_2,
                        parent,
                        false
                )
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

        return super.onOptionsItemSelected(item)
    }

   inner class PointFilter: InputFilter{
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence {
            if(source == null || source.isBlank()){
                return ""
            }
            Log.i(TAG, "added text $source, it has length ${source.length} characters")
            val validCharTypes = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
            for (inputChar in source) {
                val type = Character.getType(inputChar)
                Log.i(TAG, "Character type $type")
               if (!validCharTypes.contains(type)){
                   Toast.makeText(this@MainActivity, "Only numbers are allowed", Toast.LENGTH_SHORT).show()
                   return ""
               }
            }
            return source
        }

    }

    private fun showAlertDialog() {

      val editText = EditText(this)
       val pointfilter = PointFilter()
        val lengthFilter= InputFilter.LengthFilter(3)
        editText.filters = arrayOf(lengthFilter, pointfilter)
        val editText2 = EditText(this)
        editText2.filters = arrayOf(lengthFilter)

        val SellerName = ""
        val dialog = AlertDialog.Builder(this)
                .setTitle("Sell your book")
                .setView(R.layout.dialogbox)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("SELL", null)
                .show()
        val nameofBook = findViewById<View>(R.id.editTextBookName) as EditText
        val pointsofBook = findViewById<View>(R.id.editTextBookPoints) as EditText


        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            Log.i(TAG, "Clicked on positive button!")

            val BookPoints = nameofBook.text.toString().trim()
            val BookName = pointsofBook.text.toString().trim()


            if (BookPoints.isBlank()){
                Toast.makeText(this, "Cannot submit empty book points :(", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (BookName.isBlank()){
                Toast.makeText(this, "Cannot submit empty book name :(", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val currentUser = auth.currentUser

            if (currentUser == null){
                Toast.makeText(this, "No signed in user :(", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val items = hashMapOf(
                "BookName" to BookName,
                "Points" to BookPoints,
                "Seller" to SellerName,
            )

            db.collection("SellOffer").document("TradeDetails").set(items).addOnSuccessListener { void: Void -> Toast.makeText(
                this,
                "Successfully uploaded",
                Toast.LENGTH_SHORT
            ).show() }
                  //  .update("Points", BookPoints)
            dialog.dismiss()


        }


    }
}