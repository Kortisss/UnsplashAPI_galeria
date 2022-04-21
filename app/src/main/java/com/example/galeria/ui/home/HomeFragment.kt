package com.example.galeria.ui.home
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.galeria.databinding.FragmentHomeBinding
import com.example.galeria.models.randomImageModel.Image
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val clientId = "zxQxVwX0NyRLHKKXGXyNiXIQzY0Q2iT6esxgxi2MJOo"
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (!homeViewModel.getURL().value.isNullOrEmpty()) {
            binding.imageToLoad.visibility = View.VISIBLE
            Glide
                .with(requireContext())
                .load(homeViewModel.getURL().value)
                .centerCrop()
                .into(binding.imageToLoad)
        }
        binding.imageToLoad.setOnLongClickListener{
            vibratePhone()
            homeViewModel.getObj().value?.let { obj ->
                ImageLongClickDialogFragment(obj,homeViewModel).show(
                    childFragmentManager, ImageLongClickDialogFragment.TAG)
            }
            registerForContextMenu(binding.imageToLoad)
            return@setOnLongClickListener true
        }
        binding.btnGenerateImage.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                binding.progressBar.isVisible = true
                binding.imageToLoad.visibility = View.VISIBLE
                val response = try {
                    RetrofitInstance.API.getRandomImage(clientId,"portrait")
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
                    homeViewModel.setObj(myClass)
                    Glide
                        .with(requireContext())
                        .load(myClass.urls.regular)
                        .centerInside()
                        .into(binding.imageToLoad)
                } else {
                    Log.e(TAG, "Response not successful")
                    Log.e(TAG, response.toString())
                }
                binding.progressBar.isVisible = false
            }
        }
        return root
    }
    private fun Fragment.vibratePhone() {
        val vibrator = requireContext().getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}