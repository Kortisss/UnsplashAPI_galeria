package com.example.galeria.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.galeria.databinding.FragmentNotificationsBinding
import com.example.galeria.ui.dashboard.DashboardViewModel
import com.example.galeria.ui.dashboard.ImageClickDialogFragment
import com.example.galeria.ui.dashboard.ImagesPageAdapter
import com.example.galeria.ui.home.ImageLongClickDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    //private val notificationsViewModel: NotificationsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imageAdapter = ImageUrlsAdapter(ImageUrlsAdapter.OnClickListener{ click ->
            ImageClickDialogFragmentNotifications(click,notificationsViewModel).show(childFragmentManager, ImageLongClickDialogFragment.TAG)
        })
        binding.apply {
            recyclerViewImages.apply {
                adapter = imageAdapter
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            }
        }
        notificationsViewModel.image.observe(viewLifecycleOwner){
            imageAdapter.submitList(it)
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}