package `in`.global.agro.ui.fragments

import `in`.global.agro.R
import `in`.global.agro.databinding.FragmentHomeBinding
import `in`.global.agro.ui.adapter.HomeAdapter
import `in`.global.agro.utils.Resource
import `in`.global.agro.viewmodels.HomeViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Home : Fragment(R.layout.fragment_home) {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeAdapter:HomeAdapter
    private val viewModel by viewModels<HomeViewModel>()

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)

        setUpRecyclerView()

        val deviceId = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        Log.i("TAG", "onViewCreated: $deviceId")



        viewModel.getStatusList(
            "5",deviceId,"110","49542621","1"
        )



        lifecycleScope.launchWhenStarted {
           viewModel.statusFlow.collect {
               when(it){
                   is Resource.Success ->{
                       homeAdapter.differ.submitList(it.data)
                       Log.i("TAG", "onViewCreated:${it.data} ")
                   }
                   is Resource.Error ->{
                       Log.i("TAG", "onViewCreated error:${it.message} ")
                   }
                   is Resource.Loading ->{

                   }
               }

           }

        }


    }




    private fun setUpRecyclerView(){
        homeAdapter= HomeAdapter()
        binding.homeRecyclerView.apply {
            adapter=homeAdapter
            layoutManager=LinearLayoutManager(requireContext())
        }

    }




}