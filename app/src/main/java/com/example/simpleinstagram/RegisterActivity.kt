package com.example.simpleinstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.simpleinstagram.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signUpToolbar.setOnClickListener {

            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



    }

    fun sign_up_button (view: View) {

        val sign_up_name = binding.signUpName.text.toString()
        val sign_up_email = binding.signUpEmail.text.toString()
        val sign_up_password = binding.signUpPassword.text.toString()
        val sign_up_repassword = binding.signUpRepassword.text.toString()


        if(sign_up_name.isEmpty() || sign_up_email.isEmpty() || sign_up_password.isEmpty() || sign_up_repassword.isEmpty()) {

            Toast.makeText(applicationContext,"You cannot be empty area",Toast.LENGTH_SHORT).show()

        } else if(!(sign_up_password.equals(sign_up_repassword))) {

            Toast.makeText(applicationContext,"Check your password",Toast.LENGTH_SHORT).show()
        }

        else {

            auth.createUserWithEmailAndPassword(sign_up_email,sign_up_password).addOnSuccessListener {

                val intent = Intent(this@RegisterActivity,HomeActivity::class.java)
                startActivity(intent)
                finish()


            }.addOnFailureListener {

                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun sign_in_textview(view: View) {

        val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
        startActivity(intent)

    }

}