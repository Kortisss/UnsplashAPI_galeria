package com.example.galeria.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.galeria.models.RandomImageModel.Image
import com.example.galeria.databinding.FragmentHomeBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private val client_id = "zxQxVwX0NyRLHKKXGXyNiXIQzY0Q2iT6esxgxi2MJOo"
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        /*
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        */
        if (!homeViewModel.getURL().value.isNullOrEmpty()) {
            binding.imageToLoad.visibility = View.VISIBLE
            Glide
                .with(requireContext())
                .load(homeViewModel.getURL().value)
                .centerCrop()
                .into(binding.imageToLoad)
        }

        binding.btnGenerateImage.setOnClickListener(){
            lifecycleScope.launchWhenCreated {
                binding.progressBar.isVisible = true
                binding.imageToLoad.visibility = View.VISIBLE
                val response = try {
                    RetrofitInstance.API.getRandomImage(client_id)
                } catch (e: IOException) {
                    Log.e(TAG, "IOException, you might not have internet connection")
                    binding.progressBar.isVisible = false
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e(TAG, "HttpException, unexpected response")
                    binding.progressBar.isVisible = false
                    return@launchWhenCreated
                }

                if (response.isSuccessful && response.body() != null){
                    val myClass: Image = response.body()!!
                    homeViewModel.setURL(myClass.urls.regular)
                    Glide
                        .with(requireContext())
                        .load(myClass.urls.regular)
                        .centerCrop()
                        .into(binding.imageToLoad)

                    //var json = JSONObject(response.body()) // toString() is not the response body, it is a debug representation of the response body
                    //var status = json.getString("raw")
                    //Log.e(TAG, status)
                } else {
                    Log.e(TAG, "Response not successful")
                    Log.e(TAG, response.toString())
                }
                binding.progressBar.isVisible = false
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}