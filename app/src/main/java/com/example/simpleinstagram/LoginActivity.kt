package com.example.simpleinstagram

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleinstagram.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

    }

    fun sign_in_button (view:View) {

        val sign_in_email = binding.signInEmail.text.toString()
        val sign_in_password = binding.signInPassword.text.toString()

        if(sign_in_email.isEmpty() || sign_in_password.isEmpty()) {

            Toast.makeText(applicationContext,"Please enter your email and password",Toast.LENGTH_SHORT).show()

        } else {

            auth.signInWithEmailAndPassword(sign_in_email,sign_in_password).addOnSuccessListener {

                val intent = Intent(this@LoginActivity,HomeActivity::class.java)
                startActivity(intent)
                finish()


            }.addOnFailureListener {

                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }



    }

    fun sign_up_textview (view: View) {

        val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun forgot_password_textview (view: View) {

        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.forgot_password)

        val emailBox = dialog.findViewById<EditText>(R.id.emailBox)
        val btnGonder = dialog.findViewById<Button>(R.id.btnGonder)
        val btnIptal = dialog.findViewById<Button>(R.id.btnIptal)

        btnGonder.setOnClickListener {



        }

        btnGonder.setOnClickListener {

            val forgot_password_email = emailBox.text.trim().toString()

            if(forgot_password_email.isEmpty()) {

                Toast.makeText(applicationContext,"Please enter your email",Toast.LENGTH_SHORT).show()

            } else if (!Patterns.EMAIL_ADDRESS.matcher(forgot_password_email).matches()) {

                Toast.makeText(applicationContext,"Invalid email",Toast.LENGTH_SHORT).show()
            } else {

                auth.sendPasswordResetEmail(forgot_password_email).addOnSuccessListener {

                    Toast.makeText(applicationContext,"Check your email box",Toast.LENGTH_LONG).show()
                    dialog.cancel()

                }.addOnFailureListener {

                    Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_SHORT).show()

                }

            }


        }

        btnIptal.setOnClickListener {

            dialog.cancel()

        }



        dialog.show()




    }


}

