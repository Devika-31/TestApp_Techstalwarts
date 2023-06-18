package com.india.testingapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.india.testingapp.databinding.ActivityLoginBinding
import com.india.testingapp.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()
    lateinit var pref:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        pref = getSharedPreferences("session", MODE_PRIVATE)
        if(pref.getString("isLogin","0") == "1"){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return
        }
        setContentView(binding.root)
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this

        binding.loginButton.setOnClickListener {
            val res = loginViewModel.validateInput()
            if(res.first){
                loginViewModel.loginUser()
            }else{
                Toast.makeText(this,res.second,Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.verifyResponseLiveData.observe(this){
            binding.progress.isVisible = false
            binding.loginButton.isEnabled = true
            when(it){
                is NetworkResult.Error -> {
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progress.isVisible = true
                    binding.loginButton.isEnabled = false
                }
                is NetworkResult.Success -> {
                    pref.edit().putString("isLogin","1").apply()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }
        }


    }
}