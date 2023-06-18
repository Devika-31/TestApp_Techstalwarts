package com.india.testingapp.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.india.testingapp.LoginActivity
import com.india.testingapp.databinding.LogoutFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment:Fragment() {

    lateinit var binding: LogoutFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LogoutFragmentBinding.inflate(layoutInflater)
        binding.logoutButton.setOnClickListener {
           val pref = requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
           pref.edit().putString("isLogin","0").apply()
           startActivity(Intent(requireContext(),LoginActivity::class.java))
           requireActivity().finish()
        }
        return binding.root
    }
}