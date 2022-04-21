package com.example.galeria.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.galeria.R
import com.example.galeria.databinding.FragmentDashboardBinding
import com.example.galeria.models.pageOfImagesModel.PageModel
import com.example.galeria.models.pageOfImagesModel.Result
import com.example.galeria.ui.home.ImageLongClickDialogFragment
import com.example.galeria.ui.home.RetrofitInstance
import com.example.galeria.ui.home.TAG
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException
@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val clientId = "zxQxVwX0NyRLHKKXGXyNiXIQzY0Q2iT6esxgxi2MJOo"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        //code here
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.API.getListOfImages(clientId,"house")
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                val listOfImages: PageModel = response.body()!!
                dashboardViewModel.image.value = listOfImages.results
            } else {
                Log.e(TAG, "Response not successful")
                Log.e(TAG, response.toString())
            }

            //onClick function for items in adapter
            val imageAdapter = ImagesPageAdapter(ImagesPageAdapter.OnClickListener{click ->
                ImageClickDialogFragment(click).show(childFragmentManager, ImageClickDialogFragment.TAG)
            })
            binding.apply {
                recyclerViewImagesDashboard.apply {
                    adapter = imageAdapter
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                }

            }
            dashboardViewModel.image.observe(viewLifecycleOwner){
                imageAdapter.submitList(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}