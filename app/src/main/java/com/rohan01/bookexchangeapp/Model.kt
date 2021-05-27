package com.rohan01.bookexchangeapp

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


  data class BookNames(var title: String) {

    private companion object {
        private val TAG = "Model"

    }

    object Supplier {


       var booknames = mutableListOf<BookNames>()


       var db = FirebaseFirestore.getInstance()

       val docRef = db.collection("SellOffer")
               .get()
               .addOnSuccessListener { result ->
           for (document in result) {
               Log.d(TAG, "${document.id} => ${document.getString("BookName")}")
               booknames.add(BookNames("${document.getString("BookName")}"))

           }
       }
               .addOnFailureListener { exception ->
                   Log.d(TAG, "Error getting documents: ", exception)
               }

    }
  }

 data class BookLinks(var link: String) {

     private companion object {
         private val TAG = "Model"

     }

     object Supplier {


         var booklinks = mutableListOf<BookLinks>()




         var db = FirebaseFirestore.getInstance()

         val docRef = db.collection("SellOffer")
                 .get()
                 .addOnSuccessListener { result ->
                     for (document in result) {
                         Log.d(BookLinks.TAG, "${document.id} => ${document.getString("BookLink")}")
                         booklinks.add(BookLinks("${document.getString("BookLink")}"))

                     }
                 }
                 .addOnFailureListener { exception ->
                     Log.d(BookLinks.TAG, "Error getting documents: ", exception)
                 }

     }





 }
