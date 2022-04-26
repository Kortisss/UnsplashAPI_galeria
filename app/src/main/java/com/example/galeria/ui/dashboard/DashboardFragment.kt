package com.example.galeria.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.galeria.R
import com.example.galeria.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow



@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val TAG = "DashboardFragment"
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    //todo: zaszyfrować klusz clientId
    override fun onResume() {
        super.onResume()

        val itemsColor = listOf("white", "black_and_white", "black", "yellow", "orange", "red", "magenta", "green", "teal", "blue")
        val adapterColor = ArrayAdapter(requireContext(), R.layout.filter_option_list_item, itemsColor)
        (binding.colorsList as? AutoCompleteTextView)?.setAdapter(adapterColor)

        val itemsOrderBy = listOf("latest", "relevant")
        val adapterOrderBy = ArrayAdapter(requireContext(), R.layout.filter_option_list_item, itemsOrderBy)
        (binding.orderByList as? AutoCompleteTextView)?.setAdapter(adapterOrderBy)


        if(!dashboardViewModel.queryString.value.isNullOrEmpty()){
            dashboardViewModel.returnImages()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        var isDown = false

        dashboardViewModel.loading.observe(viewLifecycleOwner){
            if (dashboardViewModel.loading.value == true){
                binding.progresBarDashboard.visibility=View.VISIBLE
            }else{
                binding.progresBarDashboard.visibility=View.INVISIBLE
            }
        }

        binding.orderByList.setOnItemClickListener { parent: AdapterView<*>?, _: View?, position: Int, _: Long ->
            dashboardViewModel.queryOrderByOption.value = parent?.getItemAtPosition(position).toString()
            dashboardViewModel.returnImages()
        }
        binding.colorsList.setOnItemClickListener { parent: AdapterView<*>?, _: View?, position: Int, _: Long ->
            dashboardViewModel.queryColorOption.value = parent?.getItemAtPosition(position).toString()
            dashboardViewModel.returnImages()
        }
        binding.searchViewDashboard.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                dashboardViewModel.queryString.value = p0
                dashboardViewModel.returnImages()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        //onClick function for items in adapter
        val imageAdapter = ImagesPageAdapter(ImagesPageAdapter.OnClickListener{click ->
            ImageClickDialogFragment(click,dashboardViewModel).show(childFragmentManager, ImageClickDialogFragment.TAG)
        })
        binding.apply {
            recyclerViewImagesDashboard.apply {
                adapter = imageAdapter
                layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!recyclerView.canScrollVertically(1) && dy > 0)
                        {
                            dashboardViewModel.nextPage()
                        }else if (!recyclerView.canScrollVertically(-1) && dy < 0)
                        {
                            //scrolled to TOP
                            Log.d("-----", "top")
                        }
                    }
                })
            }
        }
        dashboardViewModel.image.observe(viewLifecycleOwner){
            imageAdapter.submitList(it)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}