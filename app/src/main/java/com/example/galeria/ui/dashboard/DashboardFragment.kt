package com.example.galeria.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.galeria.R
import com.example.galeria.databinding.FragmentDashboardBinding
import com.example.galeria.models.pageOfImagesModel.PageModel
import com.example.galeria.ui.home.RetrofitInstance
import com.example.galeria.ui.home.TAG
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val clientId = "zxQxVwX0NyRLHKKXGXyNiXIQzY0Q2iT6esxgxi2MJOo"
    override fun onResume() {
        super.onResume()

        val itemsColor = listOf("white", "black_and_white", "black", "yellow", "orange", "red", "magenta", "green", "teal", "blue")
        val adapterColor = ArrayAdapter(requireContext(), R.layout.filter_option_list_item, itemsColor)
        (binding.colorsList as? AutoCompleteTextView)?.setAdapter(adapterColor)

        val itemsOrderBy = listOf("latest", "relevant")
        val adapterOrderBy = ArrayAdapter(requireContext(), R.layout.filter_option_list_item, itemsOrderBy)
        (binding.orderByList as? AutoCompleteTextView)?.setAdapter(adapterOrderBy)


        returnImages()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        var isDown = false
        //list of items for query color selection


        binding.orderByList.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            dashboardViewModel.queryOrderByOption.value = parent?.getItemAtPosition(position).toString()
            returnImages()
        }
        binding.colorsList.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            dashboardViewModel.queryColorOption.value = parent?.getItemAtPosition(position).toString()
            returnImages()
        }

        binding.searchViewDashboard.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                dashboardViewModel.queryString.value = p0
                returnImages()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        //dashboardViewModel.queryString.value = binding.searchViewDashboard.query.toString()
        //returnImages()
        //code here

        binding.imageArrow.setOnClickListener{
            fun menuDown(){
                binding.menuColor.visibility = View.VISIBLE
                binding.menuOrderBy.visibility = View.VISIBLE

                binding.menuOrderBy.animate().alpha(1f)
                binding.menuColor.animate().alpha(1f)
                binding.imageArrow.animate().rotationBy(180f)
                isDown = true
            }
            fun menuUp(){
                binding.menuOrderBy.animate().alpha(0f)
                binding.menuColor.animate().alpha(0f)
                binding.imageArrow.animate().rotationBy(180f)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.menuColor.visibility = View.GONE
                    binding.menuOrderBy.visibility = View.GONE
                }, 300)
                isDown = false
            }
            if (!isDown){
                menuDown()
            }else {
                menuUp()
            }
        }

        return binding.root
    }

    private fun returnImages(){
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.API.getListOfImages(clientId,
                    dashboardViewModel.queryString.value.toString(),
                    dashboardViewModel.queryOrderByOption.value.toString(),
                    dashboardViewModel.queryColorOption.value.toString())//todo: Wykorzystałem limit requestów na godzine :D nie pobierami obrazow
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
                ImageClickDialogFragment(click,dashboardViewModel).show(childFragmentManager, ImageClickDialogFragment.TAG)
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
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}