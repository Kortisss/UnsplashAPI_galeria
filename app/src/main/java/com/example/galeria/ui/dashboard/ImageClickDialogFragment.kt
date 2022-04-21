package com.example.galeria.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.galeria.models.pageOfImagesModel.Result
import com.example.galeria.models.randomImageModel.Urls
import com.example.galeria.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageClickDialogFragment(obj: Result, viewModel: DashboardViewModel): DialogFragment() {
    private val items = arrayOf<CharSequence>("Add to favourite", "View Online")
    private val myClass: Result = obj
    private val vm: DashboardViewModel = viewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setItems(items) { _, id ->
                    when (id) {
                        0 -> {
                            val obj = Urls(myClass.urls.full, myClass.urls.full,myClass.urls.raw,
                                myClass.urls.regular,myClass.urls.small,myClass.urls.small_s3)
                            lifecycleScope.launchWhenCreated{
                                vm.postImageToDatabase(obj)
                            }
                            Toast.makeText(requireContext(),"Image added!", Toast.LENGTH_LONG).show()
                        }
                        1 -> {
                            val browserIntent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(myClass.links.html))
                            startActivity(browserIntent)
                        }
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    companion object {
        const val TAG = "DashboardFragment_ImageClick"
    }
}