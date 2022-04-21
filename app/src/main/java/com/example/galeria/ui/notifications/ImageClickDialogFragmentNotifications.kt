package com.example.galeria.ui.notifications

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.galeria.models.randomImageModel.Urls
import com.example.galeria.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageClickDialogFragmentNotifications(obj: Urls, viewModel:NotificationsViewModel): DialogFragment() {
    private val vm: NotificationsViewModel = viewModel
    private val items = arrayOf<CharSequence>("Remove from favourites")
    private val newObj: Urls = obj
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setItems(items) { _, id ->
                    when (id) {
                        0 -> {
                            val objToRemove = Urls(newObj.full,newObj.raw,newObj.regular,newObj.small,newObj.small_s3,newObj.thumb,newObj.likedImageId)
                            lifecycleScope.launchWhenCreated{
                                vm.removeImageToDatabase(objToRemove)
                            }
                            Toast.makeText(requireContext(),"Image removed!",Toast.LENGTH_LONG).show()
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