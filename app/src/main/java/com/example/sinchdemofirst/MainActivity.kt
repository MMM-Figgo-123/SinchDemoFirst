package com.example.sinchdemofirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import com.example.sinchdemofirst.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var  binding: ActivityMainBinding

    lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

        }
    }


    fun loginUser(){

        var email = binding.etEmail.text
        var password = binding.etPassword.text

        if (validate()){

            binding.progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener {

                    if (it.isSuccessful){
                        //open next Activity
                        binding.progressBar.visibility = View.GONE

                        val intent =Intent(this, HomeActivity::class.java)
                        intent.putExtra("CALLER","LOGIN")
                        startActivity(intent)

                    }else{
                        binding.progressBar.visibility = View.GONE

                        Toast.makeText(this,"${it.exception?.localizedMessage.toString()}",Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }
  fun  validate():Boolean{
      if (binding.etEmail.text.isEmpty()){
          Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show()
          binding.etEmail.requestFocus()
          return false
      }
      if (binding.etPassword.text.isEmpty()){
          Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show()
          binding.etPassword.requestFocus()
          return false
      }
      return true
  }
}