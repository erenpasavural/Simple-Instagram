package com.example.simpleinstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleinstagram.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        firestore =Firebase.firestore

        postArrayList = ArrayList<Post>()

        getData()

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(postArrayList)
        binding.recyclerview.adapter = adapter







    }

    fun sign_out(view: View) {

        auth.signOut()
        val intent = Intent(this@HomeActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()

    }
    fun add_post(view: View) {

        val intent = Intent(this@HomeActivity,UploadActivity::class.java)
        startActivity(intent)
    }
    fun getData() {

        firestore.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if(error != null) {

                Toast.makeText(applicationContext,error.localizedMessage,Toast.LENGTH_SHORT).show()

            } else {

                if(value != null){

                    if(!value.isEmpty) {

                        val data = value.documents

                        postArrayList.clear()

                        for(veriler in data) {

                            val comment = veriler.get("comment") as String
                            val useremail = veriler.get("userEmail") as String
                            val downloadUrl = veriler.get("downloadUrl") as String

                            val post = Post(useremail,comment,downloadUrl)
                            postArrayList.add(post)

                        }

                            adapter.notifyDataSetChanged()

                    }

                }

            }

        }

    }

}