package com.india.testingapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.india.testingapp.NetworkResult
import com.india.testingapp.databinding.FragmentHomeBinding
import com.india.testingapp.ui.home.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        {

        }
        val binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel.userListResponseLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> TODO()
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Success -> {
                    val adapterList = UserAdapter(it.data?.data!!)
                    with(binding.recyclerView) {
                        adapter = adapterList
                        layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                    }
                }
            }
        }
        return binding.root
    }

}