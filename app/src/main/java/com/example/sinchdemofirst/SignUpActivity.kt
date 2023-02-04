package com.example.sinchdemofirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sinchdemofirst.databinding.ActivitySignUpBinding
import com.example.sinchdemofirst.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var auth : FirebaseAuth
    lateinit var firebaseReference :DatabaseReference
    var user = User()

    var name = ""
    var email = ""
    var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firebaseReference = FirebaseDatabase.getInstance().reference.child("Users")


        binding.btnSignUp.setOnClickListener {

            name = binding.etName.text.toString()
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            signUpUser()
        }

        binding.tvLogin.setOnClickListener {
           onBackPressed()

        }
    }
    fun signUpUser(){

        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()

        if (validate()){

            binding.progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (it.isSuccessful){
                        //open next Activity
                        Toast.makeText(this,"User Created Successfully",Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                        startActivity(Intent(this,MainActivity::class.java))
                      var firebaseUser = auth.currentUser

                        user.name = name
                        user.email = email
                        user.password = password
                        user.userId = firebaseUser?.uid.toString()

                        firebaseReference.child(firebaseUser?.uid!!).setValue(user)
                            .addOnCompleteListener {



                            }

                    }else{
                        binding.progressBar.visibility = View.GONE

                        Toast.makeText(this,"Signup Failed",Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

    fun  validate():Boolean{
        if (binding.etName.text.isEmpty()){
            Toast.makeText(this,"Enter Name", Toast.LENGTH_SHORT).show()
            binding.etName.requestFocus()
            return false
        }
        if (binding.etEmail.text.isEmpty()){
            Toast.makeText(this,"Enter Email", Toast.LENGTH_SHORT).show()
            binding.etEmail.requestFocus()
            return false
        }
        if (binding.etPassword.text.isEmpty()){
            Toast.makeText(this,"Enter Password", Toast.LENGTH_SHORT).show()
            binding.etPassword.requestFocus()
            return false
        }

        return true
    }
}