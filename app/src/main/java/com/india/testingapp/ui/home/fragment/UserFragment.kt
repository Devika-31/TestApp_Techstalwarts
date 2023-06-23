package com.india.testingapp.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.india.testingapp.NetworkResult
import com.india.testingapp.databinding.UserFragmentBinding
import com.india.testingapp.ui.home.HomeViewModel
import com.india.testingapp.ui.home.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment :Fragment(){
    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = UserFragmentBinding.inflate(layoutInflater)
        viewModel.getUserData()
        viewModel.userListResponseLiveData.observe(viewLifecycleOwner){
            binding.progress.isVisible = false
            when(it){
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    binding.progress.isVisible = true
                }
                is NetworkResult.Success -> {
                    val userAdapterList = UserAdapter(it.data?.data?: arrayListOf())
                    with(binding.recyclerView) {
                        adapter = userAdapterList
                        layoutManager = LinearLayoutManager(requireContext(),
                            RecyclerView.VERTICAL,false)
                    }
                }
            }
        }
        return binding.root
    }
}